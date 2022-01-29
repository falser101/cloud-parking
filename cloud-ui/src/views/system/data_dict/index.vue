<template>
  <div>
    <el-row>
      <el-col :span="4">
        <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText">
        </el-input>
        <el-tree
          v-loading="loading"
          class="filter-tree"
          :data="dataDict"
          :props="defaultProps"
          @node-click="handleNodeClick"
          :filter-node-method="filterNode"
          ref="tree"
        ></el-tree>
      </el-col>
      <el-col :span="20">
        <el-row>
          <el-button size="small" @click="handleAdd">新增字典</el-button>
          <el-button size="small" @click="handleUpdate">修改字典</el-button>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-descriptions direction="vertical" :column="2" border>
            <el-descriptions-item label="节点名称">{{ dict.dictName }}</el-descriptions-item>
            <el-descriptions-item label="节点编码">{{ dict.dictCode }}</el-descriptions-item>
            <el-descriptions-item label="父节点名称">{{ dict.parentName }}</el-descriptions-item>
            <el-descriptions-item label="父节点编码">{{ dict.parentCode }}</el-descriptions-item>
            <el-descriptions-item label="备注">{{ dict.dictRemark }}</el-descriptions-item>
          </el-descriptions>
        </el-row>
      </el-col>
    </el-row>
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form1" v-loading="formLoading" :model="form1" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="节点名称" prop="dictName">
              <el-input v-model="form1.dictName" placeholder="请输入节点名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级节点">
              <el-input disabled v-model="form1.parentDictName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="节点编码" prop="dictCode">
              <el-input v-model="form1.dictCode" placeholder="请输入节点编码"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="节点描述">
              <el-input type="textarea" v-model="form1.dictRemark" placeholder="请输入节点描述"/>
            </el-form-item>
          </el-col>
          <el-col :sapn="24">
            <el-form-item>
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
  data() {
    return {
      loading: true,
      filterText: '',
      title: '',
      open: false,
      dict: {},
      dataDict: [],
      defaultProps: {
        children: 'childNodes',
        label: 'dictName'
      },
      reqForm: {
        parentCode: undefined,
        status: undefined
      },
      form1: {
        dictName: undefined,
        dictCode: undefined,
        parentId: 0,
        parentDictName: undefined,
        parentCode: undefined,
        dictRemark: undefined
      },
      formLoading: true,
      rules: {
        dictCode: [
          { required: true, message: '节点编码不能为空', trigger: 'blur' }
        ],
        dictName: [
          { required: true, message: '节点名称不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    /**
     * 过滤节点
     * @param value
     * @param data
     * @returns {boolean}
     */
    filterNode(value, data) {
      if (!value) return true
      console.log(data)
      return data.dictName.indexOf(value) !== -1
    },
    /**
     * 新增
     */
    handleAdd() {
      this.title = '新增节点'
      this.form1.dictName = undefined
      this.form1.dictCode = undefined
      this.form1.dictRemark = undefined
      this.open = true
      this.formLoading = false
    },
    /**
     * 修改
     */
    handleUpdate() {
      this.title = '修改节点'
      this.open = true
      this.formLoading = false
    },
    /**
     * 提交
     */
    handleSubmit() {
      this.$store.dispatch('dataDict/saveDataDict', this.form1).then(data => {
        this.dataDict = data
        this.open = false
        this.fetchData()
      })
    },
    /**
     * 点击节点
     * @param data
     */
    handleNodeClick(data) {
      const { id, dictName, dictCode } = data
      this.form1.parentId = id
      this.form1.parentDictName = dictName
      this.form1.parentCode = dictCode
      this.$store.dispatch('dataDict/getDataDict', id).then(data => {
        this.dict = data
      })
    },
    /**
     * 获取数据
     */
    fetchData() {
      this.$store.dispatch('dataDict/getDataDictLevel', this.reqForm).then(data => {
        this.dataDict = data
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
.filter-tree {
  font-size: 16px;
}
</style>
