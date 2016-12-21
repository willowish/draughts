import template from './index.html'
import './indexStyle.scss'
import appModule from '../app.module'

import BoardCtrl from './board/boardCtrl';
appModule.directive('board', BoardCtrl);

import FieldCtrl from './board/field/fieldCtrl';
appModule.directive('field', FieldCtrl);

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
