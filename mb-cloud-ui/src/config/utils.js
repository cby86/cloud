export default {
  copyFromTo(data, target) {
    if (data) {
      Object.keys(target).forEach(v => {
        target[v] = data[v]
      })
    }
  },
  listToTree(data, parentId, filter) {
    let temp = [];
    let treeArr = data;
    treeArr.forEach((item, index) => {
      if (!filter || filter(item)) {
        if (item.parentId == parentId) {
          let children = this.listToTree(treeArr, treeArr[index].id, filter);
          if (children.length > 0) {
            item.children = children;
            item.leaf = true;
          }
          temp.push(item);
        }
      }
    });
    return temp;
  }
};
