<template>
  <el-main>
    <el-tabs v-model="activeTab" type="card" @tab-remove="removeTab" @tab-click="tabClick">
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
                name: option.cacheName[option.cacheName.length-1],
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
      '$route'(to,from) {
        this.clearCache(to,from)
      },
      "tabs": {
        handler() {
          if (window.localStorage) {
            window.localStorage.setItem("cacheTabs", JSON.stringify(this.tabs))
          }
        },
        deep: true
      }
    },
    mounted() {

    },
    methods: {
      init() {
        if (window.localStorage) {
          let cacheTabs = window.localStorage.getItem("cacheTabs");
          if (cacheTabs) {
            let cache = JSON.parse(cacheTabs);
            this.tabs = cache.filter(item => !item.closable || this.$store.getters.hasAuth(item.cacheName[0]))
            this.tabs.forEach(item => {
              item.cacheName.forEach(cache => {
                this.cacheTag.push(cache)
              })
            })
          }
        }
        if (this.tabs.length == 0) {
          this.tabs = [
            {
              name: "1",
              title: "首页",
              route: "Home",
              cacheName: ["Home"],
              closable: false
            }
          ];
          this.cacheTag = ["Home"];
        }
      },
      clearCache(to, from) {
        let tab = this.findCurrentTab()
        if (tab) {
          if (tab.cacheName[tab.cacheName.length - 1] == to.name) {
            return
          }
          if (!from.meta.cache) {
            this.cacheTag = this.cacheTag.filter(name => name !== from.name)
            tab.cacheName = tab.cacheName.filter(name => name !== from.name)
          }
          if (tab.cacheName[tab.cacheName.length - 1] == to.name) {
            return
          }
          tab.cacheName.push(to.name);
          this.cacheTag.push(to.name)
        }
      },
      findCurrentTab() {
        for (let option of this.tabs) {
          if (option.name === (this.activeTab + "")) {
            return option;
          }
        }
        return null;
      },
      tabClick(tab) {
        var tagPath;
        let currentTab = this.findCurrentTab();
        if (!currentTab) {
          return
        }
        tagPath = currentTab.cacheName[currentTab.cacheName.length - 1]
        if (this.$route.name === tagPath) {
          return
        }
        this.$router.replace({name: tagPath});
      },
      matchTab(to) {
        let flag = false;
        for (let option of this.tabs) {
          if (option.route === to.params.menuPath) {
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
          if (index < this.tabs[i].name) {
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
          cacheName: [cacheName],
          closable: true
        });
        this.cacheTag.push(menuPath);
        this.activeTab = newTabName;
      },
      removeTab(targetName, tab) {
        let tabs = this.tabs;
        let activeName = this.activeTab;
        let cacheName;
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
        if (cacheName) {
          for (let name in cacheName) {
            this.cacheTag = this.cacheTag.filter(index => index !== name)
          }
        }
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
