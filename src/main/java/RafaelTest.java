import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RafaelTest {

    @Test
    public void taxTest() {
        init();
        double salary = 7000;
        double totalTax = calcTax(salary);
        Assert.assertEquals(600d, totalTax, 0.3);

    }

    private void init() {
        /**
         * 0-3000 - 0%
         * 3001-5000 - 10%
         * 5001-8000 - 20%
         * 8001-10000 - 30%
         * 10001+ - 40%
         *
         *
         * should be support add or changes tax level without changes in source code  ->
         *      supply a controller that add/remove/changed it and re-sorted the list.
         *      so, the calc func could not be changed !!
         */
        salaryTaxRanks.add(new SalaryTaxRank(0d, 3000, 0));
        salaryTaxRanks.add(new SalaryTaxRank(3001d, 5000, 10));
        salaryTaxRanks.add(new SalaryTaxRank(5001d, 8000, 20));
        salaryTaxRanks.add(new SalaryTaxRank(8001d, 10000, 30));
        salaryTaxRanks.add(new SalaryTaxRank(10001d, Double.MAX_VALUE, 40));

        Collections.sort(salaryTaxRanks);

        // if SalaryTaxRank don't implements Comparable<SalaryTaxRank> - use :
//        Collections.sort(salaryTaxRanks, Comparator.comparing(SalaryTaxRank::getMinSalary));
    }

    List<SalaryTaxRank> salaryTaxRanks = new ArrayList<>();

    @Data
    @AllArgsConstructor
    //TODO add validations: min value must be smaller than max value & not overlap rank
    class SalaryTaxRank implements Comparable<SalaryTaxRank> {
        Double minSalary;
        double maxSalary;
        double taxPercent;

        @Override
        public int compareTo(SalaryTaxRank o) {
            return this.minSalary.compareTo(o.minSalary);
        }
    }

    private double calcTax(double salary) {
//        double totalTax = 0;
//        for (SalaryTaxRank salaryTaxRank : salaryTaxRanks) {
//            if (salary <= salaryTaxRank.minSalary)
//                break;
//            totalTax += (Math.min(salary, salaryTaxRank.maxSalary) - salaryTaxRank.minSalary) * salaryTaxRank.taxPercent / 100; // take sum of tax of current level and added it to the total tax
//        }
//        return totalTax;

        return salaryTaxRanks.stream()
                .takeWhile(salaryTaxRank -> salary > salaryTaxRank.minSalary)
                .mapToDouble(salaryTaxRank -> (Math.min(salary, salaryTaxRank.maxSalary) - salaryTaxRank.minSalary) * salaryTaxRank.taxPercent / 100)
                .sum();

    }
}
