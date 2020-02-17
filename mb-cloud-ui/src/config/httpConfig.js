import axios from 'axios'
import router from "../router";

const instance = axios.create({
  headers: {
    'X-Requested-With':"X-Requested-With"
  },
  baseURL: 'api',
  timeout: 5000,
  withCredentials: true
})

// 添加请求拦截器
instance.interceptors.request.use(config => {
  // 在发送请求之前做某事，比如说 设置token
  let token = sessionStorage.getItem('token');
  if (token) {
    config.headers['Authorization'] = "Bearer "+token;
  }
  return config;
}, error => {
  // 请求错误时做些事
  return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(response => {
  // 对响应数据做些事
  return response;
}, error => {
  if (error.response) {
    if(error.response.status===503 || error.response.status===504) {
      return Promise.reject("网络错误")
    }
    if (error.response.status === 401) {
      router.push({path: "/login"})
    }
    return Promise.reject(error.response.data); // 返回接口返回的错误信息
  }
  return Promise.reject("网络错误");
})

export default instance;
