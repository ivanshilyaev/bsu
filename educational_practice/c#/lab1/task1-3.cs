using System;

namespace laba_1
{
    class Program
    {
        // radiuses for task 2
        static double r1=10, r2=5;
        static void Main(string[] args)
        {
            // task 1; variant 14
            double beginX=-4, endX=12, d=1;
            Console.WriteLine("TASK 1:");
            Console.WriteLine("Function value table for function #14:");
            Console.WriteLine("{0,10} {1,10}", "x", "f(x)");
            for (double i=beginX; i<=endX; i+=d) {
                Console.WriteLine("{0,10} {1,10}", i, f(i));   // formatted output
            }
            Console.WriteLine();

            // task 2, variant 14
            Console.WriteLine("TASK 2:");
            double x, y; string buf;
            Console.WriteLine("Enter ten points (x,y):");
            for (int i=0; i<3; ++i) {
                Console.Write("x: "); buf = Console.ReadLine(); x = Convert.ToDouble(buf);
                Console.Write("y: "); buf = Console.ReadLine(); y = Convert.ToDouble(buf);
                if (isHitted(x, y)) {Console.WriteLine("Hit!");}
                else {Console.WriteLine("Miss!");}
            }
            Console.WriteLine();

            // task 3, variant 14
            Console.WriteLine("TASK 3:");
            Console.WriteLine("Taylor series for cos function:");
            double eps = 0.0001;
            double a=0, b=0; getBoundaries(ref a, ref b);
            d = System.Math.Abs(b-a)/5;
            Console.WriteLine("{0} {1} {2}", "x", "cos(x)", "n");
            for (double i=a; i<=b; i+=d) {
                int n=0;
                Console.WriteLine("{0,7}   {1:0.000,7}   {2,7}", i, fTaylor(i, eps, ref n), n);
            }
        }

        // function for task 1
        static double f(double x) {
            if (x>=-4 && x<=0) {return x/4;}
            else if (x>=0 && x<=2) {return x*x;}
            else if (x>=2 && x<=12) {return -0.5 * x + 5;}
            return 0;
        }

        // function for task 2
        static bool isHitted(double x, double y) {
            if ( ((x>=0 && y>=0) || (x<=0 && y<=0)) && (x*x + y*y)>=r2*r2 && (x*x + y*y)<=r1*r1)
                return true;
            return false;
        }

        // functions for task 3
        // static int factorial(int n) {
        //     if (n<=0) {return 1;}
        //     else {
        //         int result=1;
        //         for (int i=1; i<=n; ++i) {result*=i;}
        //         return result;
        //     }
        // }

        // static double func(double x, int n) {
        //     return System.Math.Pow(-1, n)*System.Math.Pow(x, 2*n)/factorial(2*n);
        // }
        
        static double fTaylor(double x, double eps, ref int n) {
            double sum=1, s1=1, s2=0; n=1;
            do {
                s2=s1;
                s1*=(-1)*Math.Pow(x, 2)/((2*n-1)*(2*n));
                n++;
                sum+=s1;
            } while (System.Math.Abs(s1 - s2) > eps);
            return sum;
        }

        static void getBoundaries(ref double a, ref double b) {
            string buf;
            Console.WriteLine("Enter left boundary:");
            buf = Console.ReadLine(); a = Convert.ToDouble(buf);
            Console.WriteLine("Enter right boundary:");
            buf = Console.ReadLine(); b = Convert.ToDouble(buf);
            while (b<=a) {
                Console.WriteLine("Invalid data!");
                Console.WriteLine("Enter right boundary:");
                buf = Console.ReadLine(); b = Convert.ToDouble(buf);
            }
        }
    }
}
