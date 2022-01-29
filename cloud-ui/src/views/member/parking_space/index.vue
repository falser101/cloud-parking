<template>
  <div>
    <el-form ref="form" :inline="true" :model="form" label-width="120px">
      <el-form-item label="车位号">
        <el-input v-model="form.parkingSpaceNum"/>
      </el-form-item>
      <el-form-item label="楼层">
        <el-input v-model="form.floor"/>
      </el-form-item>
      <el-form-item label="车位类型">
        <el-select v-model="form.type" placeholder="请选择">
          <el-option
            v-for="item in typeOptions"
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
      <el-table-column label="车位号">
        <template slot-scope="scope">
          {{ scope.row.parkingSpaceNum }}
        </template>
      </el-table-column>
      <el-table-column label="楼层" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.floor }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车位大小" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.spaceSize }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车位类型" prop="type" align="center" :formatter="typeFormat">
      </el-table-column>
      <el-table-column label="备注" align="center">
        <template slot-scope="scope">
          {{ scope.row.remark }}
        </template>
      </el-table-column>
      <el-table-column label="使用状态" prop="usingStatus" align="center" :formatter="dataDictFormat">
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
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form1" v-loading="formLoading" :model="form1" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="车位号" :prop="isView ? undefined : 'parkingSpaceNum'">
              <el-input :disabled="isView" v-model="form1.parkingSpaceNum" controls-position="right"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在楼层" :prop="isView ? undefined : 'floor'">
              <el-input :disabled="isView" v-model="form1.floor" controls-position="right"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车位类型" :prop="isView ? undefined :'type'">
              <el-select :disabled="isView" v-model="form1.type" placeholder="请选择">
                <el-option
                  v-for="item in typeOptions"
                  :key="item.dictCode"
                  :label="item.dictName"
                  :value="item.dictCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input :disabled="isView" v-model="form1.remark" controls-position="right"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="isView" label="使用状态">
              <el-select :disabled="isView" v-model="form1.usingStatus" placeholder="请选择">
                <el-option
                  v-for="item in usingStatusOptions"
                  :key="item.dictCode"
                  :label="item.dictName"
                  :value="item.dictCode">
                </el-option>
              </el-select>
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
      formLoading: true,
      isView: false,
      typeOptions: [],
      usingStatusOptions: [],
      usingStatusMap: {},
      form1: {},
      form: {
        size: 10,
        current: 0
      },
      total: 0,
      listLoading: true,
      list: [],
      rules: {
        parkingSpaceNum: [{required: true, message: '车位编号不能为空', trigger: 'blur'}],
        floor: [{required: true, message: '所在楼层不能为空', trigger: 'blur'}],
        type: [{required: true, message: '车位类型不能为空', trigger: 'blur'}],
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    /**
     *
     */
    fetchData() {
      this.loadDataDict('PARKING_SPACE_TYPE', data => {
        this.typeOptions = data
      })
      this.$store.dispatch('parking/spaceList', this.form).then(data => {
        const {records, size, total, current} = data
        this.list = records
        this.form.size = size
        this.form.current = current
        this.total = total
        this.listLoading = false
      })
    },
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
    handleAdd() {
      this.form1 = {}
      this.loadDataDict('PARKING_SPACE_TYPE', data => {
        this.typeOptions = data
      })
      this.open = true
      this.isView = false
      this.formLoading = false
    },
    /**
     *
     */
    handleSearch(row) {
      this.open = true
      this.isView = true
      this.loadDataDict('PARKING_SPACE_TYPE', data => {
        this.typeOptions = data
      })
      this.loadDataDict('USING_STATUS', data => {
        this.usingStatusOptions = data
        this.usingStatusMap = this.usingStatusOptions.map(option => {
          const {dictCode, dictName} = option
          const res = {}
          res[dictCode] = dictName
          return res;
        })
        console.log(this.usingStatusMap)
      })
      this.$store.dispatch('parking/getSpace', row.id).then(data => {
        this.form1 = data
        this.formLoading = false
      })
    },
    /**
     *
     */
    handleUpdate() {
      this.open = true
      this.isView = false
      this.loadDataDict('PARKING_SPACE_TYPE', data => {
        this.typeOptions = data
      })
      this.formLoading = false
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
      this.$store.dispatch('parking/addSpace', this.form1).then(data => {
        this.open = false
        this.fetchData()
      })
    },
    /**
     *
     * @param row
     */
    dataDictFormat(row) {
      switch (row.usingStatus) {
        case "UNUSED":
          return '未使用';
        case "IN_USE":
          return '使用中';
      }
    },
    /**
     *
     * @param row
     */
    typeFormat(row) {
      switch (row.type) {
        case "TEMPORARY":
          return '临时停车位';
        case "RENTING":
          return '租赁车位';
        case "PURCHASE":
          return '被购买的车位'
      }
    }
  }
}
</script>

<style scoped>

</style>
