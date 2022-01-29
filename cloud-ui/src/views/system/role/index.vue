<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="form" label-width="120px">
      <el-form-item label="角色名">
        <el-input v-model="form.roleName"/>
      </el-form-item>
      <el-form-item label="角色KEY">
        <el-input v-model="form.roleKey"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button @click="onCancel">重置</el-button>
        <el-button @click="handleAdd">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="角色名称">
        <template slot-scope="scope">
          {{ scope.row.roleName }}
        </template>
      </el-table-column>
      <el-table-column label="角色key" align="center">
        <template slot-scope="scope">
          {{ scope.row.roleKey }}
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
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleSearch(scope.row)"
          >查看
          </el-button>
          <el-button
            v-if="scope.row.roleKey!=='super_admin'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-popconfirm
            @confirm="handleDelete(scope.row)"
            v-if="scope.row.roleKey!=='super_admin'"
            title="确定删除吗？"
          >
            <el-button
              slot="reference"
              size="mini"
              type="text"
              icon="el-icon-delete"
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
    <el-dialog :title="title" @close="form1.parentId=''" :visible.sync="open" width="680px" append-to-body>
      <el-form v-loading="dialogLoading" ref="form1" :model="form1" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色名" prop="roleName">
              <el-input :disabled="isView" v-model="form1.roleName" placeholder="请输入角色名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色KEY" prop="roleKey">
              <el-input :disabled="isView" v-model="form1.roleKey" placeholder="请输入角色KEY"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单权限">
              <el-tree
                :readonly="isView"
                ref="menu"
                :data="menuList"
                show-checkbox
                node-key="id"
                :default-checked-keys="form1.menuList"
                :props="defaultProps"
              >
              </el-tree>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="接口权限">
              <el-tree
                :readonly="isView"
                ref="interface"
                :data="interfaceList"
                show-checkbox
                node-key="id"
                :default-checked-keys="form1.interfaceList"
                :props="defaultProps"
              >
              </el-tree>
            </el-form-item>
          </el-col>
          <el-col v-if="!isView" :sapn="24">
            <el-form-item>
              <el-button type="primary" @click="handleSubmit">确定</el-button>
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

export default {
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
      open: false,
      title: undefined,
      list: null,
      listLoading: true,
      form1: {},
      interfaceList: [],
      menuList: [],
      defaultProps: {
        children: 'children',
        label: 'permissionName'
      },
      // 表单校验
      rules: {
        roleKey: [
          { required: true, message: '角色KEY不能为空', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '角色名不能为空', trigger: 'blur' }
        ]
      },
      dialogLoading: true,
      form: {
        roleName: undefined,
        roleKey: undefined,
        current: 0,
        size: 10
      },
      isView: false,
      isAdd: false,
      total: 0
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 页码切换
    currentChange(current) {
      this.form.current = current
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      this.$store.dispatch('role/getRoleList', this.form).then(data => {
        const { records, total, size, current } = data
        this.list = records
        this.form.current = current
        this.form.size = size
        this.total = total
        this.listLoading = false
      })
    },
    onSubmit() {
      this.fetchData()
    },
    handleAdd() {
      this.title = '新增角色'
      this.reset()
      this.loadRole()
      this.isAdd = true
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    handleDelete(row) {
      // return delMenu(row.menuId)
      this.$store.dispatch('role/delRole', row.id).then(data => {
        this.fetchData()
      })
    },
    handleUpdate(row) {
      this.title = '修改角色'
      this.reset()
      this.loadRole()
      this.$store.dispatch('role/getRoleById', row.id).then(data => {
        this.form1 = data
        this.dialogLoading = false
      })
    },
    handleSearch(row) {
      this.title = '查看角色'
      this.reset()
      this.loadRole()
      this.isView = true
      this.$store.dispatch('role/getRoleById', row.id).then(data => {
        this.form1 = data
        this.dialogLoading = false
      })
    },
    loadRole() {
      this.$store.dispatch('permission/getPermissionList', {
        permissionType: 'MENU',
        current: 0,
        size: 1000
      }).then(data => {
        const { records } = data
        this.menuList = records
      })

      this.$store.dispatch('permission/getPermissionList', {
        permissionType: 'INTERFACE',
        current: 0,
        size: 1000
      }).then(data => {
        const { records } = data
        this.interfaceList = records
      })
      this.dialogLoading = false
    },
    handleSubmit() {
      const reqForm = {
        id: this.form1.id,
        roleName: this.form1.roleName,
        roleKey: this.form1.roleKey,
        menuList: this.$refs.menu.getCheckedKeys(),
        interfaceList: this.$refs.interface.getCheckedKeys()
      }
      if (this.isAdd) {
        this.$store.dispatch('role/addRole', reqForm).then(() => {
          this.$message.success('新增成功')
          this.open = false
        })
      } else {
        this.$store.dispatch('role/updateRole', reqForm).then(() => {
          this.$message.success('修改成功')
          this.open = false
        })
      }
    },
    reset() {
      this.open = true
      this.isView = false
      this.form1 = {
        id: undefined,
        roleKey: undefined,
        roleName: undefined,
        interfaceList: [],
        menuList: []
      }
    }
  }
}
</script>
