export default {
   copyFromTo(data,target) {

      if(data) {
        Object.keys(target).forEach(v => {
          target[v] = data[v]
        })
      }
   }
};
