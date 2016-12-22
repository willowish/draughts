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

            vm.selectedPiece = undefined;

            boardResource.get({}, (b)=> {
                vm.board = b.board;
            });

            vm.makeMove = (field, Y, X) => {
                if (!vm.selectedPiece) {
                    vm.selectPiece(field, Y, X);
                    return;
                }
                if (!moveIsPossible(vm.board[Y][X])) {
                    return;
                }
                vm.board[Y][X].piece = vm.selectedPiece.piece;
                let oldPos = {
                    Y: vm.selectedPiece.originalPosition.Y,
                    X: vm.selectedPiece.originalPosition.X
                };
                if(Math.abs(oldPos.X-X)==2){
                	vm.board[(oldPos.Y+Y)/2][(oldPos.X+X)/2].piece = null;
                }
                vm.board[oldPos.Y][oldPos.X].piece = null;
                boardResource.update({board:vm.board, win:false}, (data) => {
                	if(data.win){
                		alert("Brawo. Wygrałeś.");
                	}
                	vm.board = data.board;
                });
                vm.selectedPiece = undefined;

            };

            vm.selectPiece = (field, Y, X) => {
                if (field.piece && field.piece.color==="WHITE") {
                    vm.selectedPiece = {
                        piece: field.piece,
                        originalPosition: {Y: Y, X: X}
                    }
                }
            }

        }
    }
}

let moveIsPossible = (field) => {
    return field.color == 'BLACK';
};
