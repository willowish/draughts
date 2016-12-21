import template from "./fieldTemplate.html";
import "./fieldStyle.scss";

export default ()=> {
    return {
        restrict: 'E',
        replace: false,
        scope: {
            item: '='
        },
        template: template,
        controllerAs: 'vm',
        controller($scope) {
            let vm = this;
            vm.getFieldColor = () => {
                return $scope.item.color.toLowerCase();
            };

            vm.getPieceColor = ()=> {
                return 'piece-' + $scope.item.piece.color.toLowerCase();
            }
        }
    }
}
