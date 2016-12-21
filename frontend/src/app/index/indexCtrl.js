import template from './index.html'
import './indexStyle.scss'
import appModule from '../app.module'


appModule.directive('index', () => {
	return{
		restrict: 'E',
		replace: false,
		scope:{},
		template: template,
		controllerAs:'vm',
		controller($scope) {
			let vm = this;
			
		}
	};
});
export default appModule;
