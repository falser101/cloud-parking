<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="form" label-width="120px">
      <el-form-item label="用户名">
        <el-input v-model="form.userName"/>
      </el-form-item>
      <el-form-item label="登录名">
        <el-input v-model="form.loginName"/>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.mobile"/>
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
      <el-table-column align="center" label="ID">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="用户名">
        <template slot-scope="scope">
          {{ scope.row.userName }}
        </template>
      </el-table-column>
      <el-table-column label="登录名" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.loginName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.mobile }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上次登录时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.pageviews }}
        </template>
      </el-table-column>
      <el-table-column label="上次登录ip" align="center">
        <template slot-scope="scope">
          {{ scope.row.pageviews }}
        </template>
      </el-table-column>
      <el-table-column label="上次密码更新时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.pageviews }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
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
            v-if="scope.row.loginName!=='admin'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-popconfirm
            v-if="scope.row.loginName!=='admin'"
            title="确定删除吗？"
            @confirm="handleDelete(scope.row)"
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
    </el-table>
    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" @close="form1.parentId=''" :visible.sync="open" width="680px" append-to-body>
      <el-form v-loading="dialogLoading" ref="form1" :model="form1" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" :prop="isView ? undefined : 'userName'">
              <el-input :disabled="isView" v-model="form1.userName" placeholder="请输入用户名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录名" :prop="isView ? undefined : 'loginName'">
              <el-input :disabled="isView" v-model="form1.loginName" placeholder="请输入登录名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" :prop="isView ? undefined : 'mobile'">
              <el-input :disabled="isView" v-model="form1.mobile" placeholder="请输入手机号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" :prop="isView ? undefined : 'email'">
              <el-input :disabled="isView" v-model="form1.email" placeholder="请输入邮箱"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input :disabled="isView" v-model="form1.remark" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" :prop="isView ? undefined : 'status'">
              <el-select :disabled="isView" v-model="form1.status" placeholder="请选择状态">
                <el-option
                  v-for="item in statusOptions"
                  :key="item.dictCode"
                  :label="item.dictName"
                  :value="item.dictCode"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" :prop="isView ? undefined : 'sex'">
              <el-select :disabled="isView" v-model="form1.sex" placeholder="请选择状态">
                <el-option
                  v-for="item in sexOptions"
                  :key="item.dictCode"
                  :label="item.dictName"
                  :value="item.dictCode"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户类型" :prop="isView ? undefined : 'userType'">
              <el-select :disabled="isView" v-model="form1.userType" placeholder="请选择用户类型">
                <el-option
                  v-for="item in userTypeOptions"
                  :key="item.dictCode"
                  :label="item.dictName"
                  :value="item.dictCode"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色" :prop="isView ? undefined : 'roles'">
              <el-select :disabled="isView" v-model="form1.roles" placeholder="请选择角色">
                <el-option
                  v-for="item in roleList"
                  :key="item.id"
                  :label="item.roleName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :sapn="24">
            <el-form-item v-if="!isView">
              <el-button type="primary" @click="handleSubmit">确定</el-button>
              <el-button @click="open = false">取消</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
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
      list: null,
      total: 0,
      listLoading: true,
      roleList: [],
      form: {
        loginName: undefined,
        userName: undefined,
        mobile: undefined,
        size: 10,
        current: 1
      },
      userDetail: {},
      open: false,
      dialogLoading: true,
      title: '',
      isView: false,
      isAdd: false,
      form1: {},
      statusOptions: [],
      userTypeOptions: [],
      sexOptions: [],
      // 表单校验
      rules: {
        mobile: [
          { required: true, message: '角色KEY不能为空', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '角色名不能为空', trigger: 'blur' }
        ],
        loginName: [
          { required: true, message: '登录名不能为空', trigger: 'blur' }
        ],
        userType: [
          { required: true, message: '用户类型不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '用户状态不能为空', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '用户性别不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    handleCheckedCitiesChange() {

    },
    loadDataDict() {
      this.$store.dispatch('dataDict/getDataDictLevel', {
        parentCode: 'STATUS'
      }).then(data => {
        const { childNodes } = data[0]
        this.statusOptions = childNodes
      })
      this.$store.dispatch('dataDict/getDataDictLevel', {
        parentCode: 'USER_TYPE'
      }).then(data => {
        const { childNodes } = data[0]
        this.userTypeOptions = childNodes
      })
      this.$store.dispatch('dataDict/getDataDictLevel', {
        parentCode: 'SEX'
      }).then(data => {
        const { childNodes } = data[0]
        this.sexOptions = childNodes
      })
      this.$store.dispatch('role/getRoleList', {
        current: 1,
        size: 1000
      }).then(data => {
        const { records } = data
        this.roleList = records
      })
    },
    fetchData() {
      this.listLoading = true
      this.$store.dispatch('user/userList', this.form).then(data => {
        const { records, total, size, current } = data
        this.list = records
        this.form.size = size
        this.form.current = current
        this.total = total
        this.listLoading = false
      })
    },
    getUserById(id) {
      this.$store.dispatch('user/userDetail', id).then(data => {
        this.form1 = data
        const { roles } = data
        this.form1.roles = roles[0].id
      })
    },
    onSubmit() {
      this.fetchData()
    },
    onCancel() {
      //
    },
    /** 新增按钮 */
    handleAdd() {
      this.loadDataDict()
      this.form1 = {}
      this.open = true
      this.title = '新增用户'
      this.isView = false
      this.dialogLoading = false
      this.isAdd = true
    },
    /**  */
    handleSubmit() {
      if (this.isAdd) {
        this.$store.dispatch('user/addUser', this.form1).then(data => {
          this.open = false
          this.fetchData()
        })
      } else {
        this.$store.dispatch('user/updateUser', this.form1).then(data => {
          this.open = false
          this.fetchData()
        })
      }
    },
    /**
     * 查看
     * @param row
     */
    handleSearch(row) {
      this.loadDataDict()
      this.title = '查看用户'
      this.open = true
      this.isView = true
      this.dialogLoading = false
      this.getUserById(row.id)
    },
    /**
     * 修改
     * @param row
     */
    handleUpdate(row) {
      this.loadDataDict()
      this.title = '修改用户'
      this.open = true
      this.isView = false
      this.dialogLoading = false
      this.getUserById(row.id)
      this.isAdd = false
    },
    handleDelete(row) {
      // return delMenu(row.menuId)
      this.$store.dispatch('user/delUser', row.id).then(data => {
        this.fetchData()
      })
    }
  }
}
</script>
