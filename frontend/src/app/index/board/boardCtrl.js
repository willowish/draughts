import template from "./boardTemplate.html";
import "./boardStyle.scss";

export default ()=> {
    return {
        restrict: 'E',
        replace: false,
        scope: {},
        template: template,
        controllerAs: 'vm',
        controller($scope, boardResource) {
            let vm = this;
            boardResource.get({}, (b)=>vm.board = b);
        }
    }
}
