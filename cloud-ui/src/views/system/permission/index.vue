<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="form" label-width="120px">
      <el-form-item label="路由名称">
        <el-input v-model="form.routerName" />
      </el-form-item>
      <el-form-item label="权限key">
        <el-input v-model="form.perms" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button @click="onCancel">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-has-permi="['system:permission:add']" type="primary" @click="addMenuOrInterface('0', '')">
          新增
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="路由name">
        <template slot-scope="scope">
          {{ scope.row.routerName }}
        </template>
      </el-table-column>
      <el-table-column label="权限类型">
        <template slot-scope="scope">
          {{ scope.row.permissionType }}
        </template>
      </el-table-column>
      <el-table-column label="权限perms" align="center">
        <template slot-scope="scope">
          {{ scope.row.perms }}
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center">
        <template slot-scope="scope">
          {{ scope.row.createBy }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center">
        <template slot-scope="scope">
          {{ scope.row.updateBy }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.updateTime }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.permissionType !== 'BUTTON'"
            v-has-permi="['system:permission:add']"
            size="mini"
            type="text"
            icon="el-icon-add"
            @click="addMenuOrInterface(scope.row.id, scope.row.permissionType)"
          >新增
          </el-button>
          <el-button
            v-has-permi="['system:permission:update']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            @confirm="handleDelete(scope.row)"
          >
            <el-button
              slot="reference"
              v-has-permi="['system:permission:remove']"
              size="mini"
              type="text"
            >删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
      <div slot="empty">
        <p><span>暂无数据</span></p>
      </div>
    </el-table>
    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body @close="form1.parentId=''">
      <el-form ref="form1" v-loading="formLoading" :model="form1" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="父级路由" prop="parentId">
              <el-select v-model="form1.parentId" disabled clearable placeholder="请选择">
                <el-option
                  key="0"
                  label="一级目录"
                  value="0"
                />
                <el-option
                  v-for="item in level1"
                  :key="item.id"
                  :label="item.routerName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              v-if="form1.permissionType === 'CONTENTS' || form1.permissionType === 'MENU'"
              label="路由name"
              prop="routerName"
            >
              <el-input v-model="form1.routerName" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form1.permissionType === 'BUTTON'" prop="perms" label="权限标识">
              <el-input v-model="form1.perms" placeholder="请输入权限标识" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :sapn="24">
            <el-form-item>
              <el-button type="primary" @click="updatePermission">确定</el-button>
              <el-button @click="open = false">取消</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :current-page="form.current"
      :page-size="form.size"
      @current-change="currentChange"
      @prev-click="currentChange"
      @next-click="currentChange"
    />
  </div>
</template>

<script>

import IconSelect from '@/components/IconSelect'

export default {
  components: { IconSelect },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      level1: [],
      options: [],
      value1: [],
      // 表单参数
      form1: {},
      // 菜单树选项
      menuOptions: [],
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: '父级权限不能为空', trigger: 'blur' }
        ],
        permissionType: [
          { required: true, message: '权限类型不能为空', trigger: 'blur' }
        ],
        routerName: [
          { required: true, message: '前端路由name不能为空', trigger: 'blur' }
        ],
        perms: [
          { required: true, message: '权限字符不能为空', trigger: 'blur' }
        ]
      },
      // 弹出层标题
      title: '新增目录',
      // 是否显示弹出层
      open: false,
      formLoading: true,
      isAdd: false,
      // 是否展开，默认全部折叠
      isExpandAll: false,
      list: [],
      total: 0,
      listLoading: true,
      form: {
        permissionType: 'CONTENTS',
        routerName: undefined,
        perms: undefined,
        current: 1,
        size: 10
      },
      permissionTypes: [
        {
          value: 'CONTENTS',
          label: '目录'
        },
        {
          value: 'MENU',
          label: '菜单'
        },
        {
          value: 'BUTTON',
          label: '按钮'
        }
      ]
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    /**
     * 加载数据权限
     */
    loadDataDict(parentCode, callback) {
      this.$store.dispatch('dataDict/getDataDictLevel', {
        parentCode: parentCode
      }).then(data => {
        const { childNodes } = data[0]
        callback(childNodes)
      })
    },
    /** 修改权限 */
    updatePermission() {
      this.$refs['form1'].validate((valid) => {
        if (valid) {
          if (this.isAdd) {
            this.$store.dispatch('permission/addPermission', this.form1).then(() => {
              this.open = false
            })
          } else {
            this.$store.dispatch('permission/updatePermission', this.form1).then(() => {
              this.open = false
            })
          }
          this.fetchData()
        } else {
          return false
        }
      })
    },
    /** 选择图标 */
    selected(name) {
      this.form1.icon = name
    },
    /** 加载数据 */
    fetchData() {
      this.listLoading = true
      this.$store.dispatch('permission/getPermissionList', this.form).then(data => {
        const { records, total, size, current } = data
        this.list = records
        this.form.size = size
        this.form.current = current
        this.total = total
        this.listLoading = false
      }).catch(() => {
        this.listLoading = false
      })
    },
    /** 添加菜单或接口权限 */
    addMenuOrInterface(parentId, type) {
      this.isAdd = true
      this.form1 = {}
      switch (type) {
        case 'CONTENTS':
          this.title = '新增菜单'
          this.form1.permissionType = 'MENU'
          break
        case 'MENU':
          this.title = '新增按钮'
          this.form1.permissionType = 'BUTTON'
          break
        default:
          this.form1.permissionType = 'CONTENTS'
          this.form1.routerName = ''
          break
      }
      this.form1.parentId = parentId
      if (type !== '') {
        this.$store.dispatch('permission/getLevel1MenuOrInterface', type).then(data => {
          this.level1 = data
        })
      }

      this.open = true
      this.formLoading = false
    },
    onSubmit() {
      this.fetchData()
    },
    onCancel() {
      this.form = {
        permissionName: undefined,
        perms: undefined,
        permissionType: 'MENU',
        current: 0,
        size: 10
      }
      this.fetchData()
    },
    /** 页面切换 */
    currentChange(current) {
      this.form.current = current
      this.fetchData()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.loadDataDict('METHOD', data => {
        this.options = data
      })
      this.open = true
      this.formLoading = true
      this.isAdd = false
      this.title = this.form1.permissionType === 'MENU' ? '修改菜单' : '修改接口'
      this.$store.dispatch('permission/getPermissionById', row.id).then(data => {
        this.form1 = data
        if (this.form1.parentId === 0) {
          this.form1.parentId = undefined
        }
        this.formLoading = false
      })
      this.$store.dispatch('permission/getLevel1MenuOrInterface', this.form.permissionType).then(data => {
        this.level1 = data
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== undefined) {
            // updateMenu(this.form).then(response => {
            //   this.$modal.msgSuccess('修改成功')
            //   this.open = false
            //   this.getList()
            // })
          } else {
            // addMenu(this.form).then(response => {
            //   this.$modal.msgSuccess('新增成功')
            //   this.open = false
            //   this.getList()
            // })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      // return delMenu(row.menuId)
      this.$store.dispatch('permission/delPermissionById', row.id).then(() => {
        this.fetchData()
      })
    },
    // 表单重置
    reset() {
      this.form1 = {
        id: undefined,
        parentId: '',
        permissionName: undefined,
        icon: undefined,
        permissionType: 'MENU',
        orderNum: undefined,
        visible: '0',
        status: '0',
        method: undefined,
        remark: undefined
      }
    }
  }
}
</script>
