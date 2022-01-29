<template>
  <div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="图片识别" name="first">
        <el-upload
          class="avatar-uploader"
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-tab-pane>
      <el-tab-pane label="手动输入" name="second">
        <el-form :inline="true" :model="inForm" class="demo-form-inline">
          <el-form-item label="车牌号">
            <el-input v-model="inForm.carNum" placeholder="车牌号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('in')">车辆进场</el-button>
          </el-form-item>
        </el-form>
        <el-form :inline="true" :model="outForm" class="demo-form-inline">
          <el-form-item label="车牌号">
            <el-input v-model="outForm.carNum" placeholder="车牌号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('out')">车辆出场</el-button>
          </el-form-item>
        </el-form>
        <div class="block">
          <span class="demonstration">请支付</span>
          <el-image :src="src"></el-image>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import {Message} from "element-ui";

export default {
  name: "index",
  data() {
    return {
      activeName: "first",
      inForm: {},
      outForm: {},
      imageUrl: '',
      src: '',
      payStatus: []
    }
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
    handleClick() {

    },
    /**
     *
     */
    onSubmit(type) {
      switch (type) {
        case 'in':
          this.$store.dispatch('parking/carIn', this.inForm).then(data => {

          })
          break
        case 'out':
          this.$store.dispatch('parking/carOut', this.outForm).then(data => {
            const {id, alipayQrCode} = data
            this.src = 'https://bashuculture-1301017257.cos.ap-chengdu.myqcloud.com/' + alipayQrCode
            // 每5秒查询交易结果，知道成功或者交易取消
            let endTime = 24;
            this.loadDataDict('PAY_STATUS', data => {
              this.payStatus = data
            })
            console.log(this.payStatus)
            const timer = setInterval(() => {
              endTime--;
              this.$store.dispatch('order/orderInfo', id).then(data => {
                const {payStatus} = data
                let message
                let type
                switch (payStatus) {
                  case 'TRADE_SUCCESS':
                    type = 'success'
                    message = '交易支付成功'
                    clearInterval(timer);
                    break
                  case 'WAIT_BUYER_PAY':
                    type = 'success'
                    message = '等待买家付款'
                    break
                  case 'TRADE_CLOSED':
                    type = 'warning'
                    message = '未付款交易超时关闭，或支付完成后全额退款'
                    clearInterval(timer);
                    break
                  case 'TRADE_FINISHED':
                    type = 'success'
                    message = '交易结束，不可退款'
                    clearInterval(timer);
                    break
                  default:
                    type = 'warning'
                    message = '请尽快支付'
                    break;
                }
                Message({
                  message: message,
                  type: type
                })
              })
              if (endTime === 0) {
                clearInterval(timer);
              }
            }, 5000);
          })
      }

    },
    /**
     *
     */
    handleAvatarSuccess() {
    },
    /**
     *
     */
    beforeAvatarUpload() {
    }
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
