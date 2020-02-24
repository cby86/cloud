import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/page/index'
import MenuList from '@/page/menu/MenuList'
import MenuForm from '@/page/menu/MenuForm'
import Role from '@/page/role/RoleList'
import Home from '@/page/home/Home'

const NotFound = () => import('@/page/404/NotFound.vue')
const RoleForm = () => import('@/page/role/RoleForm.vue')

Vue.use(Router)
var router = new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/page/login/Login')
    },
    {
      path: '/',
      component: Index,
      children: [
        {
          path: "/",
          name: Home.name,
          component: Home,
          meta: {
            primary: true
          }
        },
        {
          path: '/menu',
          name: MenuList.name,
          component: MenuList,
          meta: {
            primary: true
          }
        },
        {
          path: '/menu/update',
          name: MenuForm.name,
          component: MenuForm,
          meta: {
            cacheParent:true,
            primary: false,
            parent:MenuList.name
          }
        },
        {
          path: '/role',
          name: Role.name,
          component: Role,
          meta: {
            primary: true
          }
        },
        {
          path: '/role/update',
          name: RoleForm.name,
          component: RoleForm,
          meta: {
            primary: false,
            parent:Role.name
          }
        },
        {
          path: '/resource',
          name: "ResourceList",
          component: () => import('@/page/resource/ResourceList'),
          meta: {
            primary: true
          }
        },
        {
          path: '/resource/update',
          name: "ResourceForm",
          component: () => import('@/page/resource/ResourceForm'),
          meta: {
            primary: false,
            parent:"ResourceList"
          }
        },
        {
          path: '/user',
          name: "UserList",
          component: () => import('@/page/user/UserList'),
          meta: {
            primary: true
          }
        },
        {
          path: '/user/update',
          name: "UserForm",
          component: () => import('@/page/user/UserForm'),
          meta: {
            primary: false,
            parent:"UserList"
          }
        }
      ]
    }
    ,
    {path: '*', component: NotFound}
  ]
});
router.findParent = (path) => {
  let menu = findRouter(router.options.routes, path);
  return !menu || menu.name
}

function findRouter(items, path) {
  var result = null;
  for (var index in items) {
    let item = items[index];
    if (item.meta && item.meta.primary && item.path!==""  && path.startsWith(item.path)) {
      result = item
      break;
    }
    if (item.children && item.children.length > 0) {
      result = findRouter(item.children, path);
      if(result) {
        return result;
      }
    }
  }
  return result;
}

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    sessionStorage.removeItem('token');
  }
  var user = sessionStorage.getItem('token');
  if (!user && to.path !== '/login') {
    next({
      path: '/login'
    })
  } else {
    next();
  }
});

/**
 * 路由重复点击，忽略错误
 * @type {Router.push|*}
 */
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router
