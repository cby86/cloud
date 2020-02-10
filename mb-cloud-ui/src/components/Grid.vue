<template>
  <div>
    <el-table size="small"
              :data="tableData"
              style="width: 100%">
      <template v-for="(column,index) in dataColumns">
        <template v-if="!column.actions">
          <el-table-column :key="column.name" :sortable="column.sortable"
                           :formatter="column.formatter" v-if="column.show===undefined || column.show===true"
                           :prop="column.name" :fixed="column.actions?'right':''"
                           :label="column.label">
          </el-table-column>
        </template>
        <el-table-column v-else :key="column.name">
          <template slot-scope="scope" slot="header" v-if="dataColumns.length>0">
            <span v-if="index!==dataColumns.length-1">{{column.label}}</span>
            <el-dropdown :hide-on-click="false" type="small" v-else>
                        <span class="gridHeader">
                          {{column.label}}<i class="el-icon-arrow-down el-icon--right"></i>
                        </span>
              <el-dropdown-menu slot="dropdown">
                <template v-for="(filter,i) in dataColumns">
                  <el-dropdown-item v-if="i!==dataColumns.length-1">
                    <el-checkbox type="small" :checked="filter.show===undefined || filter.show===true"
                                 @change="columnSelectorChange($event,filter)">{{filter.label}}
                    </el-checkbox>
                  </el-dropdown-item>
                </template>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
          <template slot-scope="scope">
            <el-button v-for="(action, index) in column.actions" :key="index" type="text" size="small" @click="action.handler(scope.row)">
              {{action.name}}
            </el-button>
          </template>
        </el-table-column>
      </template>
    </el-table>
  </div>
</template>

<script>
  export default {
    name: 'Grid',
    props: {
      tableData: {
        default: Array
      },
      columns: {
        default: Array
      }
    },
    created() {

    },
    data() {
      return {
        dataColumns: this.columns,
      };
    },
    mounted() {
    },
    methods: {
      columnSelectorChange(nvalue, column) {
        column.show = nvalue;
      }
    }
  };
</script>
<style>
  .gridHeader {
    font-size: 12px;
    color: #909399;
  }
</style>
