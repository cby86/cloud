import axios from './httpConfig'
import {Message, Loading} from 'element-ui'
import Qs from 'qs'

var request = {}

request.get = function ({url, config = {}, success, error}) {
  axios.get(url, config).then((res) => {
    successHand(res, success)
  }).catch((e) => {
    errorHand(e, error);
  });
};

request.post = function ({url, data, config = {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}, success, error}) {
  axios.post(url, data ? Qs.stringify(data,{allowDots: true}) : null, config).then(
    (res) => {
      successHand(res, success)
    }).catch((e) => {
    errorHand(e, error)
  });
};

function errorHand(e, error) {
  if (error) {
    error(e)
  }
  else {
    Message.error({message: e.message||"网络错误"});
  }
}

function successHand(res, success) {
  if (res.status === 200) {
    let result = res.data;
    if (success) {
      success(result);
    }
  }
}

export default request;
