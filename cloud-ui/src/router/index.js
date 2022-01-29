import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: 'Dashboard', icon: 'dashboard'}
    }]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/system',
    component: Layout,
    redirect: '/system/permission',
    name: 'system',
    meta: {
      title: '系统管理',
      icon: 'nested'
    },
    children: [
      {
        path: 'permission',
        component: () => import('@/views/system/permission/index'),
        name: 'permission',
        meta: {title: '权限管理'}
      },
      {
        path: 'role',
        component: () => import('@/views/system/role/index'),
        name: 'role',
        meta: {title: '角色管理'}
      },
      {
        path: 'user',
        component: () => import('@/views/system/user/index'),
        name: 'user',
        meta: {title: '用户管理'}
      },
      {
        path: 'dataDict',
        component: () => import('@/views/system/data_dict/index'),
        name: 'dataDict',
        meta: {title: '数据字典'}
      }
    ]
  },
  {
    path: '/parking',
    component: Layout,
    redirect: '/parking/member',
    name: 'parking',
    meta: {
      title: '会员中心',
      icon: ''
    },
    children: [
      {
        path: 'member',
        component: () => import('@/views/member/member/index'),
        name: 'member',
        meta: {title: '会员管理'}
      },
      {
        path: 'parking_space',
        component: () => import('@/views/member/parking_space/index'),
        name: 'parking_space',
        meta: {title: '停车位管理'}
      },
      {
        path: 'simulation',
        component: () => import('@/views/member/simulation/index'),
        name: 'simulation',
        meta: {title: '模拟出入场'}
      },
      {
        path: 'accessInfo',
        component: () => import('@/views/member/accessInfo/index'),
        name: 'accessInfo',
        meta: {title: '进出停车场信息'}
      },
      {
        path: 'order',
        component: () => import('@/views/member/order/index'),
        name: 'order',
        meta: {title: '停车订单信息'}
      }
    ]
  },
  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
