package threads;

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
    class SalaryTaxRank implements Comparable<SalaryTaxRank> {
        Double minSalary;
        double maxSalary;
        double taxPercent;

        public SalaryTaxRank(Double minSalary, double maxSalary, double taxPercent) {
            this.minSalary = minSalary;
            this.maxSalary = maxSalary;
            this.taxPercent = taxPercent;
        }

        @Override
        public int compareTo(SalaryTaxRank o) {
            return this.minSalary.compareTo(o.minSalary);
        }
    }

    private double calcTax(double salary) {
        double totalTax = 0;
        for (SalaryTaxRank currentRankMin : salaryTaxRanks) {
            if (salary <= currentRankMin.minSalary)
                break;
            totalTax += (Math.min(salary, currentRankMin.maxSalary) - currentRankMin.minSalary) * currentRankMin.taxPercent / 100;
        }

        return totalTax;
    }
}
