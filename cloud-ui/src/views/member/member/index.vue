<template>
  <div>
    <el-form ref="form" :inline="true" :model="form" label-width="120px">
      <el-form-item label="会员名">
        <el-input v-model="form.usernme"/>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.mobile"/>
      </el-form-item>
      <el-form-item label="会员类型">
        <el-select v-model="form.memberType" placeholder="请选择">
          <el-option
            v-for="item in memberOptions"
            :key="item.dictCode"
            :label="item.dictName"
            :value="item.dictCode">
          </el-option>
        </el-select>
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
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.mobile }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车牌号" align="center">
        <template slot-scope="scope">
          {{ scope.row.licensePlateNumber }}
        </template>
      </el-table-column>
      <el-table-column prop="memberType" :formatter="memberTypeFormat" label="会员类型" align="center">
      </el-table-column>
      <el-table-column label="激活时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.activeTime }}
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
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
          >
            <el-button
              slot="reference"
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
            >删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--  新增或修改  -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body @close="form1.parentId=''">
      <el-form ref="form1" v-loading="formLoading" :model="form1" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" :prop="isView ? undefined : 'username'">
              <el-input :disabled="isView" v-model="form1.username" controls-position="right"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" :prop="isView ? undefined : 'mobile'">
              <el-input :disabled="isView" v-model="form1.mobile" controls-position="right"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" :prop="isView ? undefined :'sex'">
              <el-select :disabled="isView" v-model="form1.sex" placeholder="请选择">
                <el-option
                  v-for="item in sexOptions"
                  :key="item.dictCode"
                  :label="item.dictName"
                  :value="item.dictCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌号">
              <el-input :disabled="isView" v-model="form1.licensePlateNumber" controls-position="right"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会员类型" :prop="isView ? undefined : 'memberType'">
              <el-select :disabled="isView" v-model="form1.memberType" placeholder="请选择">
                <el-option
                  v-for="item in memberOptions"
                  :key="item.dictCode"
                  :label="item.dictName"
                  :value="item.dictCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车位号">
              <el-autocomplete
                class="inline-input"
                :disabled="isView"
                v-model="form1.parkingSpaceNum"
                :fetch-suggestions="querySearch"
                placeholder="请输入内容"
                @select="handleSelect"
              ></el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input :disabled="isView" v-model="form1.remark" controls-position="right"/>
            </el-form-item>
          </el-col>
          <el-col :sapn="24">
            <el-form-item v-if="!isView">
              <el-button type="primary" @click="addOrUpdate">确定</el-button>
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
  name: "index",
  data() {
    return {
      title: '',
      open: false,
      form: {
        size: 10,
        current: 1
      },
      isView: false,
      isUpdate: false,
      form1: {},
      sexOptions: [],
      memberOptions: [],
      formLoading: true,
      listLoading: true,
      list: [],
      total: 0,
      restaurants: [],
      rules: {
        username: [
          {required: true, message: '用户名不能为空', trigger: 'blur'}
        ],
        mobile: [
          {required: true, message: '手机号不能为空', trigger: 'blur'}
        ],
        sex: [
          {required: true, message: '性别不能为空', trigger: 'blur'}
        ],
        memberType: [
          {required: true, message: '会员限类型不能为空', trigger: 'blur'}
        ]
      }
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
        const {childNodes} = data[0]
        callback(childNodes)
      })
    },
    /**
     *
     */
    fetchData() {
      this.loadDataDict('MEMBER_TYPE', data => {
        this.memberOptions = data
      })
      this.$store.dispatch('parking/getMemberList', this.form).then(data => {
        const {records, total, size, current} = data
        this.list = records
        this.form.size = size
        this.total = total
        this.form.current = current
        this.listLoading = false
      })
    },
    /**
     *
     */
    onSubmit() {
      this.fetchData()
    },
    /**
     *
     */
    onCancel() {
      this.form = {}
      this.fetchData()
    },
    /**
     *
     */
    loadData() {
      this.open = true
      this.form1 = {}
      this.loadDataDict('SEX', data => {
        this.sexOptions = data
      })
      this.loadDataDict('MEMBER_TYPE', data => {
        this.memberOptions = data
      })
    },
    /**
     *
     */
    handleAdd() {
      this.title = '新增会员'
      this.isView = false
      this.isUpdate = false
      this.loadData();
      this.formLoading = false
    },
    /**
     *
     */
    handleSearch(row) {
      this.title = '会员详情'
      this.isView = true
      this.isUpdate = false
      this.loadData()
      this.$store.dispatch('parking/memberInfo', row.id).then(data => {
        this.form1 = data
        this.formLoading = false
      })
    },
    /**
     *
     */
    handleUpdate(row) {
      this.title = '修改会员'
      this.isView = false
      this.isUpdate = true
      this.loadData()
      this.$store.dispatch('parking/memberInfo', row.id).then(data => {
        this.form1 = data
        this.formLoading = false
      })
    },
    /**
     *
     */
    handleDelete() {

    },
    /**
     *
     */
    addOrUpdate() {
      if (this.isUpdate) {
        this.$store.dispatch('parking/updateMember', this.form1).then(data => {
          this.open = false
          this.fetchData()
        })
      } else {
        this.$store.dispatch('parking/addMember', this.form1).then(data => {
          this.open = false
          this.fetchData()
        })
      }
    },
    /**
     *
     */
    querySearch(queryString, cb) {
      this.$store.dispatch('parking/spaceList', {
        size: 2000,
        current: 1,
        usingStatus: 'UNUSED',
        status: 'ENABLE'
      }).then(data => {
        const {records} = data
        this.restaurants = records.map(data => {
          const {parkingSpaceNum} = data
          const res = {}
          res.value = parkingSpaceNum
          return res;
        })
      })
      const restaurants = this.restaurants;
      const results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.indexOf(queryString) === 0);
      };
    },
    /**
     *
     */
    handleSelect(item) {
      console.log(item);
    },
    /**
     *
     * @param row
     */
    memberTypeFormat(row) {
      switch (row.memberType) {
        case "TEMPORARY":
          return '临时会员'
        case "REGULAR":
          return '普通会员'
        case "SUPER":
          return '超级会员'
        case "PURCHASED":
          return "已购买车位会员"
      }
    }
  }
}
</script>

<style scoped>

</style>
