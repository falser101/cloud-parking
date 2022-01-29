<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="form" label-width="120px">
      <el-form-item :label="form.permissionType !== 'MENU' ? '接口名称' : '菜单名称'">
        <el-input v-model="form.permissionName"/>
      </el-form-item>
      <el-form-item :label="form.permissionType !== 'MENU' ? '接口权限key' : '菜单权限key'">
        <el-input v-model="form.perms"/>
      </el-form-item>
      <el-form-item label="权限类型">
        <el-select @change="fetchData" v-model="form.permissionType" placeholder="请选择">
          <el-option
            v-for="item in permissionTypes"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button @click="onCancel">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addMenuOrInterface(0)">新增</el-button>
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
      <el-table-column :label="form.permissionType !== 'MENU' ? '接口名称' : '菜单名称'">
        <template slot-scope="scope">
          {{ scope.row.permissionName }}
        </template>
      </el-table-column>
      <el-table-column v-if="form.permissionType === 'MENU'" label="菜单key" align="center">
        <template slot-scope="scope">
          {{ scope.row.perms }}
        </template>
      </el-table-column>
      <el-table-column
        v-if="form.permissionType !== 'MENU'"
        label="接口请求url"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.url }}
        </template>
      </el-table-column>
      <el-table-column
        v-if="form.permissionType !== 'MENU'"
        label="接口请求方法"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.method }}
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
            v-if="scope.row.parentId === 0"
            size="mini"
            type="text"
            icon="el-icon-add"
            @click="addMenuOrInterface(scope.row.id)"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-popconfirm
            @confirm="handleDelete(scope.row)"
            title="确定删除吗？"
          >
            <el-button
              slot="reference"
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
            <el-form-item :label="form1.permissionType === 'MENU' ? '父级菜单' : '父级接口'">
              <el-select v-model="form1.parentId" clearable placeholder="为空为一级菜单，请选择">
                <el-option
                  v-for="item in level1"
                  :key="item.id"
                  :label="item.permissionName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="form1.permissionType === 'MENU' ? '菜单名称' : '接口名称'" prop="permissionName">
              <el-input v-model="form1.permissionName" placeholder="请输入名称"/>
              <span v-if="form1.permissionType === 'MENU'" slot="label">
                <el-tooltip content="前端路由中meta的title属性" placement="top">
                  <i class="el-icon-question"/>
                </el-tooltip>
                菜单名称
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form1.permissionType === 'MENU'" label="菜单图标">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected"/>
                <el-input slot="reference" v-model="form1.icon" placeholder="点击选择图标" readonly>
                  <svg-icon
                    v-if="form1.icon"
                    slot="prefix"
                    :icon-class="form1.icon"
                    class="el-input__icon"
                    style="height: 32px;width: 16px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon"/>
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form1.permissionType !== 'MENU' && form1.parentId > 0" prop="url">
              <span slot="label">
                <el-tooltip content="访问的接口请求URL，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                  <i class="el-icon-question"/>
                </el-tooltip>
                接口URL
              </span>
              <el-input v-model="form1.url" placeholder="请输入接口请求URL"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form1.permissionType !== 'MENU' && form1.parentId > 0" prop="method">
              <span slot="label">
                <el-tooltip content="" placement="top">
                  <i class="el-icon-question"/>
                </el-tooltip>
                请求方式
              </span>
              <el-select v-model="form1.method" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.dictCode"
                  :label="item.dictName"
                  :value="item.dictCode"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form1.permissionType === 'MENU'" prop="perms">
              <el-input v-model="form1.perms" placeholder="请输入权限标识" maxlength="100"/>
              <span slot="label">
                <el-tooltip content="前端路由中的name属性" placement="top">
                  <i class="el-icon-question"/>
                </el-tooltip>权限字符
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form1.orderNum" controls-position="right" :min="0"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input v-model="form1.remark" controls-position="right"/>
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
        permissionType: [
          { required: true, message: '权限类型不能为空', trigger: 'blur' }
        ],
        permissionName: [
          { required: true, message: '菜单名称不能为空', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '菜单顺序不能为空', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '路由地址不能为空', trigger: 'blur' }
        ],
        perms: [
          { required: true, message: '权限字符不能为空', trigger: 'blur' }
        ]
      },
      // 弹出层标题
      title: '',
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
        permissionName: undefined,
        perms: undefined,
        permissionType: 'MENU',
        current: 1,
        size: 10
      },
      permissionTypes: [
        {
          value: 'MENU',
          label: '菜单'
        },
        {
          value: 'INTERFACE',
          label: '接口'
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
      this.form1.permissionType = this.form.permissionType
      this.$refs['form1'].validate((valid) => {
        if (valid) {
          if (this.isAdd) {
            this.$store.dispatch('permission/addPermission', this.form1).then(data => {
              this.open = false
            })
          } else {
            this.$store.dispatch('permission/updatePermission', this.form1).then(data => {
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
    addMenuOrInterface(parentId) {
      this.loadDataDict('METHOD', data => {
        this.options = data
      })
      this.isAdd = true
      this.title = this.form.permissionType !== 'MENU' ? '添加接口' : '添加菜单'
      this.form1 = {}
      this.form1.permissionType = this.form.permissionType
      if (parentId !== 0) {
        this.form1.parentId = parentId
      }
      this.$store.dispatch('permission/getLevel1MenuOrInterface', this.form.permissionType).then(data => {
        this.level1 = data
      })
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
      this.$store.dispatch('permission/delPermissionById', row.id).then(data => {
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
