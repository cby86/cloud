<template>
  <el-select :value="valueTitle"  :clearable="clearable" @clear="clearHandle">
    <el-option :value="valueTitle" :label="valueTitle" class="options">
      <el-tree
        id="tree-option"
        ref="selectTree"
        :accordion="accordion"
        :data="options"
        :props="props"
        :node-key="props.value"
        :default-expanded-keys="defaultExpandedKey"
        :load="load"
        :lazy="lazy"
        @node-click="handleNodeClick">
      </el-tree>
    </el-option>
  </el-select>
</template>

<script>
  export default {
    name: "SelectTree",
    props: {
      // 配置项
      props: {
        type: Object,
        default() {
          return {
            value: 'id',             // ID字段名
            label: 'title',         // 显示名称
            children: 'children',
            isLeaf: "isLeaf"// 子级字段名
          }
        }
      },
      // 选项列表数据(树形结构的对象数组)
      options: {type: Array, default: () => []},


      // 初始值
      value: {type: Object, default: null},

      // 可清空选项
      clearable: {type: Boolean, default: true},

      // 自动收起
      accordion: {type: Boolean, default: false},
      load: null,
      lazy: false
    },
    data() {
      return {
        valueId: null,
        valueTitle: '',
        defaultExpandedKey: []
      }
    },
    mounted() {
      // if(this.value) {
      //   this.valueId = this.value.id    // 初始值
      //   this.valueTitle = this.value.label    // 初始值
      // }
      this.initHandle()
    },
    methods: {
      // 初始化值
      initHandle() {
        this.initScroll()
      },
      // 初始化滚动条
      initScroll() {
        this.$nextTick(() => {
          let scrollWrap = document.querySelectorAll('.el-scrollbar .el-select-dropdown__wrap')[0]
          let scrollBar = document.querySelectorAll('.el-scrollbar .el-scrollbar__bar')
          scrollWrap.style.cssText = 'margin: 0px; height: none; overflow: hidden;'
          scrollBar.forEach(ele => ele.style.width = 0)
        })
      },
      //   // 切换选项
      handleNodeClick(node) {
        this.valueTitle = node[this.props.label];
        this.valueId = node[this.props.value];
        this.$emit('getValue', this.valueId)
        this.defaultExpandedKey = []
      },
      //   // 清除选中
      clearHandle() {
        this.valueTitle = ''
        this.valueId = null
        this.defaultExpandedKey = []
        this.clearSelected()
        this.$emit('getValue', null)
      },
      // 清空选中样式
      clearSelected() {
        let allNode = document.querySelectorAll('#tree-option .el-tree-node')
        allNode.forEach((element) => element.classList.remove('is-current'))
      }
    },
    watch: {
      "value"() {
        //通过异步加载值
        if(!this.valueId && this.value) {
          this.valueId = this.value.id    // 初始值
          this.valueTitle = this.value.label    // 初始值
        }
      }
    }
  };
</script>

<style scoped>
  .el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
    height: auto;
    max-height: 274px;
    padding: 0;
    overflow: hidden;
    overflow-y: auto;
  }

  .el-select-dropdown__item.selected {
    font-weight: normal;
  }

  ul li > > > .el-tree .el-tree-node__content {
    height: auto;
    padding: 0 20px;
  }

  .el-tree-node__label {
    font-weight: normal;
  }

  .el-tree > > > .is-current .el-tree-node__label {
    color: #409EFF;
    font-weight: 700;
  }

  .el-tree > > > .is-current .el-tree-node__children .el-tree-node__label {
    color: #606266;
    font-weight: normal;
  }
</style>
