import template from './fieldTemplate.html';
import './fieldStyle.scss';

export default ()=> {
    return {
        restrict: 'E',
        replace: false,
        scope: {},
        template: template,
        controllerAs: 'vm',
        controller($scope) {
            let vm = this;
        }
    }
}
