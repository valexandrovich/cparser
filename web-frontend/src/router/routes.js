import SearchView from "@/views/SearchView.vue";
import TestView from '@/views/TestView.vue'

export const routes = [
    {
        path: '/',
        name: 'search',
        component: SearchView,
        label: 'Search',
        icon: 'magnifying-glass'
    },

    {
        path: '/test',
        name: 'test',
        component: TestView,
        label: 'Test',
        icon: 'bars-progress'
    }
]