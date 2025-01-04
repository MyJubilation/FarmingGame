import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login/Login.vue'
import Farm from '@/components/MainWeb/Farm.vue'
import Field from '@/components/MainWeb/Field.vue'
import SellStore from '@/components/MainWeb/SellStore.vue'
import FieldBuy from '@/components/MainWeb/FieldBuy.vue'
import FarmInfo from '@/components/MainWeb/FarmInfo.vue'

Vue.use(Router)

export default new Router({
	routes: [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
	{
		path: '/farm',
		name: 'Farm',
		component: Farm,
		children:[{
				path: 'field',
				name: 'Field',
				component: Field
			},
			{
				path: 'sellStore',
				name: 'SellStore',
				component: SellStore
			},
			{
				path: 'fieldBuy',
				name: 'FieldBuy',
				component: FieldBuy
			},
			{
				path: 'farmInfo',
				name: 'FarmInfo',
				component: FarmInfo
			},
		]
	}
  ]
})
