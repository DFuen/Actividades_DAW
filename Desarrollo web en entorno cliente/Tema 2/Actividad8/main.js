function esSudokuCorrecto(sudoku) {
    console.log(sudoku);

    for(let i=0;i<sudoku.length;i++){
            for (var numero of sudoku[i]) {
                for (let a=0; a < sudoku[i].length; a++) {
                    console.log(i,a);
                    if(sudoku[i][a]==numero){
                        console.log("Error");
                        
                    }
                    
                }
            }
        console.log("tabla ",i,"todo okay");
    }
        
}