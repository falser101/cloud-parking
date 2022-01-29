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
      <el-table-column prop="orderNo" label="订单号"></el-table-column>
      <el-table-column prop="paySerialNumber" label="付款流水号"></el-table-column>
      <el-table-column prop="customerId" label="下单人ID" align="center"></el-table-column>
      <el-table-column prop="payType" label="支付方式" align="center"></el-table-column>
      <el-table-column label="支付渠道" prop="payChannel" align="center"></el-table-column>
      <el-table-column label="订单金额" prop="orderMoney" align="center"></el-table-column>
      <el-table-column label="优惠金额" prop="discountMoney" align="center"></el-table-column>
      <el-table-column label="付款状态" prop="payStatus" align="center"></el-table-column>
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
      this.$store.dispatch('order/orderList', this.form).then(data => {
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
