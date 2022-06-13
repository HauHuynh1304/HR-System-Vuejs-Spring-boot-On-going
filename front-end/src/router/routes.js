import DashboardLayout from "@/layout/dashboard/DashboardLayout.vue";
import AuthLayout from "@/layout/dashboard/AuthLayout.vue";

// GeneralViews
import NotFound from "@/pages/NotFoundPage.vue";

//import middleware
import auth from "@/middleware/auth";
import guest from "@/middleware/guest";
import { FE_ROUTER_PROP } from "../constant/routerProps";

const Login = () => import(/* webpackChunkName: "pages" */ "@/pages/Login.vue");
const Register = () =>
  import(/* webpackChunkName: "pages" */ "@/pages/Register.vue");
const PasswordReset = () =>
  import(/* webpackChunkName: "password" */ "@/pages/Password/Reset.vue");
const PasswordEmail = () =>
  import(/* webpackChunkName: "password" */ "@/pages/Password/Email.vue");

// Admin pages
const Dashboard = () =>
  import(/* webpackChunkName: "dashboard" */ "@/pages/Dashboard.vue");
const Profile = () =>
  import(/* webpackChunkName: "common" */ "@/pages/Profile.vue");
const Notifications = () =>
  import(/* webpackChunkName: "common" */ "@/pages/Notifications.vue");
const Icons = () =>
  import(/* webpackChunkName: "common" */ "@/pages/Icons.vue");
const Maps = () => import(/* webpackChunkName: "common" */ "@/pages/Maps.vue");
const Typography = () =>
  import(/* webpackChunkName: "common" */ "@/pages/Typography.vue");
const TableList = () =>
  import(/* webpackChunkName: "common" */ "@/pages/TableList.vue");

// Example pages
const UserProfile = () => import("@/pages/Examples/UserProfile.vue");
// User Management
const ListUserPage = () =>
  import("@/pages/Examples/UserManagement/ListUserPage.vue");
const ListAccountsPage = () => import("../pages/Admin/ListAccouts.vue");
const NewUserPage = () => import("@/pages/Examples/UserManagement/NewUser.vue");
const UpdateUserPage = () =>
  import("@/pages/Examples/UserManagement/UpdateUserPage.vue");

let authPages = {
  path: FE_ROUTER_PROP.LOGIN.ROOT_PATH,
  redirect: FE_ROUTER_PROP.LOGIN.REDIRECT,
  component: AuthLayout,
  children: [
    {
      path: FE_ROUTER_PROP.LOGIN.CHILDREN.LOGIN.PATH,
      name: FE_ROUTER_PROP.LOGIN.CHILDREN.LOGIN.NAME,
      component: Login,
      meta: { middleware: guest },
    },
    // {
    //   path: "/register",
    //   name: "Register",
    //   component: Register,
    //   meta: { middleware: guest },
    // },
    // {
    //   path: "/password/reset",
    //   name: "Password Reset",
    //   component: PasswordReset,
    //   meta: { middleware: guest },
    // },
  ],
};

let adminMenu = {
  path: FE_ROUTER_PROP.ADMIN.ROOT_PATH,
  component: DashboardLayout,
  name: FE_ROUTER_PROP.ADMIN.ROOT_NAME,
  children: [
    {
      path: FE_ROUTER_PROP.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.PATH,
      name: FE_ROUTER_PROP.ADMIN.CHILDREN.ACCOUNT_MANAGEMENT.NAME,
      components: { default: ListAccountsPage },
      meta: { middleware: auth },
    },
  ],
};

let humanManagementMenu = {
  path: FE_ROUTER_PROP.HUMAN_MANAGEMENT.ROOT_PATH,
  component: DashboardLayout,
  name: FE_ROUTER_PROP.HUMAN_MANAGEMENT.ROOT_NAME,
  children: [
    {
      path: FE_ROUTER_PROP.HUMAN_MANAGEMENT.CHILDREN.EMPLOYEES.PATH,
      name: FE_ROUTER_PROP.HUMAN_MANAGEMENT.CHILDREN.EMPLOYEES.NAME,
      components: { default: ListUserPage },
      meta: { middleware: auth },
    },
    {
      path: FE_ROUTER_PROP.HUMAN_MANAGEMENT.CHILDREN.ADD_EMPLOYEE.PATH,
      name: FE_ROUTER_PROP.HUMAN_MANAGEMENT.CHILDREN.ADD_EMPLOYEE.NAME,
      components: { default: NewUserPage },
      meta: { middleware: auth },
    },
    {
      path: FE_ROUTER_PROP.HUMAN_MANAGEMENT.CHILDREN.UPDATE_EMPLOYEE.PATH,
      name: FE_ROUTER_PROP.HUMAN_MANAGEMENT.CHILDREN.UPDATE_EMPLOYEE.NAME,
      components: { default: UpdateUserPage },
      meta: { middleware: auth },
    },
  ],
};

const routes = [
  adminMenu,
  authPages,
  humanManagementMenu,
  {
    path: FE_ROUTER_PROP.DASHBOARD.ROOT_PATH,
    component: DashboardLayout,
    redirect: FE_ROUTER_PROP.DASHBOARD.REDIRECT,
    children: [
      {
        path: FE_ROUTER_PROP.DASHBOARD.CHILDREN.DASHBOARD.PATH,
        name: FE_ROUTER_PROP.DASHBOARD.CHILDREN.DASHBOARD.NAME,
        component: Dashboard,
        meta: { middleware: auth },
      },
      {
        path: FE_ROUTER_PROP.USER.PATH,
        name: FE_ROUTER_PROP.USER.NAME,
        components: { default: UserProfile },
        meta: { middleware: auth },
      },
      {
        path: "notifications",
        name: "notifications",
        component: Notifications,
      },
      {
        path: "icons",
        name: "icons",
        component: Icons,
      },
      {
        path: "maps",
        name: "maps",
        component: Maps,
      },
      {
        path: "typography",
        name: "typography",
        component: Typography,
      },
      {
        path: "table-list",
        name: "table-list",
        component: TableList,
      },
    ],
  },
  { path: "*", component: NotFound },
];

/**
 * Asynchronously load view (Webpack Lazy loading compatible)
 * The specified component must be inside the Views folder
 * @param  {string} name  the filename (basename) of the view to load.
function view(name) {
   var res= require('../components/Dashboard/Views/' + name + '.vue');
   return res;
};**/

export default routes;
