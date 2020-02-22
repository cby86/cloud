<template>
  <el-main >
    <el-tabs  v-model="activeTab" type="card" @tab-remove="removeTab"  @tab-click="tabClick">
      <el-tab-pane :closable="item.closable"
                   v-for="(item, index) in tabs"
                   :key="item.name"
                   :label="item.title"
                   :name="item.name"
      >
      </el-tab-pane>
    </el-tabs>
    <transition name="move" mode="out-in">
      <keep-alive :include="cacheTag">
        <router-view>
          <!-- 这里是会被缓存的视图组件 -->
        </router-view>
      </keep-alive>
    </transition>
  </el-main>
</template>
<script>
  export default {
    name: 'MainContent',
    data() {
      return {
        activeTab: "1",
        tabIndex: 1,
        cacheTag: [],
        tabs: []
      };
    },
    created() {
      // this.init();
      this.bus.$on('newMenu', (menuName, menuPath) => {
        if (menuName && menuPath) {
          this.$route.params.menuName = menuName;
          this.$route.params.menuPath = menuPath;
        }
        this.matchTab(this.$route)
      });
      this.bus.$on('tabActive', (menuName, menuPath) => {
        let openedTab = false;
        for (let option of this.tabs) {
          if (option.route === menuPath) {
            openedTab = true;
            if (this.activeTab != option.name) {
              this.activeTab = option.name
              this.$router.push({
                name: option.cacheName,
                params: {menuName: option.title, menuPath: option.route}
              })
            }
          }
        }
        if (!openedTab) {
          this.$router.push({
            name: menuPath,
            params: {menuName: menuName, menuPath: menuPath}
          })
        }
      });
      this.bus.$on('initTab', () => {
        this.init();
      });
    },
    watch: {
      '$route'(to) {
        this.clearCache(to)
      },
      "tabs"(){
        if(window.localStorage) {
          window.localStorage.setItem("cacheTabs",JSON.stringify(this.tabs))
        }
      }
    },
    mounted() {

    },
    methods: {
      init() {
        if(window.localStorage) {
          let cacheTabs = window.localStorage.getItem("cacheTabs");
          if(cacheTabs){
             let cache=JSON.parse(cacheTabs);
             this.tabs=cache.filter(item=>!item.closable || this.$store.getters.hasAuth(item.cacheName))
          }
        }
        if(this.tabs.length==0) {
          this.tabs = [
            {
              name: "1",
              title: "首页",
              route: "Home",
              cacheName: "Home",
              closable: false
            }
          ];
          this.cacheTag = ["Home"];
        }
      },
      clearCache(to) {
        let tab = this.findCurrentTab()
        if (tab) {
          let cacheName = tab.cacheName;
          this.cacheTag = this.cacheTag.filter(index => index !== cacheName)
          this.cacheTag.push(to.name)
          tab.cacheName = to.name
        }
      },
      findCurrentTab() {
        for (let option of this.tabs) {
          if (option.name ===(this.activeTab+"")) {
            return option;
          }
        }
        return null;
      },
      tabClick(tab) {
        var tagPath;
        let currentTab = this.findCurrentTab();
        if(!currentTab) {
          return
        }
        tagPath= currentTab.cacheName
        if (this.$route.name === tagPath) {
          return
        }
        this.$router.replace({name: tagPath});
      },
      matchTab(to) {
        let flag = false;
        for (let option of this.tabs) {
          if (option.route ===to.params.menuPath) {
            this.activeTab = option.name
            flag = true;
            break
          }
        }
        if (!flag) {
          this.addTab(to.params.menuName, to.params.menuPath, to.name)
        }
      },
      addTab(menuName, menuPath, cacheName) {
        let index = this.tabIndex;
        for (var i = 0; i < this.tabs.length; i++) {
          if(index < this.tabs[i].name) {
            index = this.tabs[i].name;
          }
          if (this.tabs[i].title === menuName) {
            if (this.activeTab != menuName) {
              this.activeTab = this.tabs[i].name;
            }
          }
        }
        this.tabIndex = index;
        let newTabName = ++this.tabIndex + "";
        this.tabs.push({
          title: menuName,
          name: newTabName,
          route: menuPath,
          cacheName: cacheName,
          closable: true
        });
        this.cacheTag.push(menuPath);
        this.activeTab = newTabName;
      },
      removeTab(targetName, tab) {
        let tabs = this.tabs;
        let activeName = this.activeTab;
        let cacheName = ""
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
            cacheName = tab.cacheName;
          }
        });
        this.activeTab = activeName;
        this.tabs = tabs.filter(tab => tab.name !== targetName);
        this.cacheTag = this.cacheTag.filter(index => index !== cacheName)
        if (this.tabs.length == 0) {
          this.activeTab = "1"
        }
        this.tabClick(tab)
      }
    }
  };
</script>
<style>
  .el-main {
    padding: 5px
  }
</style>
