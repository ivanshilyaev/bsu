using System;

namespace laba_1__class_
{

    class Program
    {
        static void Main(string[] args)
        {
            Plane[] planes = { new Plane("London", 654321, new Time(0, 4)),
                               new Plane("Los Angeles", 413265, new Time(19, 30)),
                               new Plane("New York", 987654, new Time(23, 50)) };
            Airport airport = new Airport(planes);
            airport.sort();
            Console.WriteLine("1. Plane by index:");
            airport.printPlanesByNumber(413265);
            Console.WriteLine("2. Plane by time:");
            airport.printPlanesByTime(new Time(23, 5));
            Console.WriteLine("3. Plane by time:");
            airport.printPlanesByDestination("New York");
            Console.WriteLine("4. Compare two planes by time:");
            Console.WriteLine(planes[0].getTime()<planes[1].getTime());
        }
    }



    class Time {
        private int hours;
        private int minutes;

        public int getHours() {
            return this.hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return this.minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public Time() {hours=0; minutes=0;}
        public Time(int hours, int minutes) {this.hours=hours%24; this.minutes=minutes%60;}

        public int getTimeInMinutes() {
            return hours * 60 + minutes;
        }

        public static bool operator < (Time t1, Time t2) {
            return t1.getTimeInMinutes() < t2.getTimeInMinutes();
        }

        public static bool operator > (Time t1, Time t2) {
            return t1.getTimeInMinutes() > t2.getTimeInMinutes();
        }

        public static bool operator <=(Time t1, Time t2) {
            return t1.getTimeInMinutes() <= t2.getTimeInMinutes();
        }

        public static bool operator >= (Time t1, Time t2) {
            return t1.getTimeInMinutes() >= t2.getTimeInMinutes();
        }
    }



    class Plane {
        private string destination;
        private int number;
        private Time time;

        public Time getTime() {
            return this.time;
        }

        public void setTime(Time time) {
            this.time = time;
        }


        public string getDestination() {
            return this.destination;
        }

        public void setDestination(string destination) {
            this.destination = destination;
        }

        public int getNumber() {
            return this.number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public Plane() {
            destination="Minsk"; number=123456; time=new Time(0, 0);
        }
        public Plane(string destination, int number, Time time) {
            this.destination=destination;
            this.number=number;
            this.time=time;
        }

        public void Print() {
            Console.WriteLine(destination);
            Console.WriteLine(number);
            if (time.getMinutes() < 10) {
                Console.WriteLine("{0}:0{1}", time.getHours(), time.getMinutes());
            }
            else {
                Console.WriteLine("{0}:{1}", time.getHours(), time.getMinutes());
            }
        }

        public static bool operator < (Plane p1, Plane p2) {
            return p1.getTime() < p2.getTime();
        }

        public static bool operator > (Plane p1, Plane p2) {
            return p1.getTime() > p2.getTime();
        }
    }



    class Airport {
        private Plane[] planes;

        public Plane[] getPlanes() {
            return this.planes;
        }

        public void setPlanes(Plane[] planes) {
            this.planes = planes;
        }


        public Airport(Plane[] planes) {
            this.planes=planes;
        }

        public void sort() {
            for (int i=0; i<planes.Length-1; ++i) {
                for (int j=i+1; j<planes.Length; ++j) {
                    if (planes[i]>planes[j]) {
                        Plane tmp = planes[i];
                        planes[i] = planes[j];
                        planes[j] = tmp;
                    }
                }
            }
        }

        public void printPlanesByNumber(int number) {
            bool found = false;
            for (int i=0; i<planes.Length; ++i) {
                if (planes[i].getNumber() == number) {
                    planes[i].Print();
                    found = true;
                }
            }
            if (!found) {Console.WriteLine("No such planes!");}
        }

        public void printPlanesByTime(Time time) {
            for (int i=0; i<planes.Length; ++i) {
                if (time.getHours()<23 || planes[i].getTime().getHours() == 23) {
                    if (planes[i].getTime().getTimeInMinutes() - time.getTimeInMinutes() <= 60)
                        planes[i].Print();
                }
                else if (planes[i].getTime().getHours() == 0) {
                    // 1440 minutes in 24  hours
                    if (planes[i].getTime().getTimeInMinutes() + 1440 - time.getTimeInMinutes() >= 0)
                        planes[i].Print();
                }
            }
        }

        public void printPlanesByDestination(string dest) {
            bool found = false;
            for (int i=0; i<planes.Length; ++i) {
                if (planes[i].getDestination().Equals(dest)) {
                    planes[i].Print();
                    found = true;
                }
            }
            if (!found) {Console.WriteLine("No such planes!");}
        }

        public void Print() {
            for (int i=0; i<planes.Length; ++i) {planes[i].Print();}
        }
    }
}
