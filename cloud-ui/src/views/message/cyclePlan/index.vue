<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="form" label-width="120px">
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
      <el-table-column label="计划名">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="类型">
        <template slot-scope="scope">
          {{ scope.row.type }}
        </template>
      </el-table-column>
      <el-table-column label="开始时间">
        <template slot-scope="scope">
          {{ scope.row.beginDate }}
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.endDate }}
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
                  :key="0"
                  label="一级目录"
                  :value="0"
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
            <el-form-item prop="permName" label="权限名">
              <el-input v-model="form1.permName" placeholder="请输入权限名" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              v-if="form1.permissionType === 'CONTENTS' || form1.permissionType === 'MENU'"
              label="路由name"
              prop="routerName"
            >
              <el-input v-model="form1.routerName" placeholder="请输入路由name" />
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
export default {
  name: 'Index',
  data() {
    return {
      form: {
        routerName: undefined,

      },
    }
  }
}
</script>

<style scoped>

</style>
