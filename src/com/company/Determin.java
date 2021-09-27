package com.company;

class Determin {
    private double summ;

    public Determin(double[][] matrix) {
        getReduction(matrix, 1);
    }


    public void getValue() {
        System.out.println("---------------------------------------------------------");
        System.out.print("Детерминант = ");
        System.out.printf("%.2f", + this.summ);
//        System.out.println();

    }


    private void getReduction(double [][] subMinor, double elemParentMinor) {
        if (subMinor.length > 1){
            double [][] tmpMinor = new double[subMinor.length - 1][subMinor[0].length - 1];
            for (int c = 0; c < subMinor[0].length; c++) {
                for (int i = 1; i < subMinor.length; i++) {
                    for (int j = 0; j < subMinor[0].length; j++) {
                        if (j < c)
                            tmpMinor[i - 1][j] = subMinor[i][j];
                        else if (j > c)
                            tmpMinor[i - 1][j - 1] = subMinor[i][j];
                    }
                }
                double paramForSub = Math.pow(-1,c+2)*subMinor[0][c]*elemParentMinor;
                getReduction(tmpMinor, paramForSub);
            }
        }
        else
            this.summ += elemParentMinor * subMinor[0][0];
    }

}
