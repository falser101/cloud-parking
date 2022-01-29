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
      <el-table-column prop="id" align="center" label="ID"></el-table-column>
      <el-table-column prop="licensePlateNumber" label="车牌号"></el-table-column>
      <el-table-column prop="entryTime" label="进场时间" align="center"></el-table-column>
      <el-table-column prop="outTime" label="出场时间" align="center"></el-table-column>
      <el-table-column label="本次停车车位号" prop="parkingSpaceNum" align="center">
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
      this.$store.dispatch('parking/accessInfoList', this.form).then(data => {
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

    },
  }
}
</script>

<style scoped>

</style>
