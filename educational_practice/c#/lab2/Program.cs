using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Text;
namespace laba
{
    struct Note
    {
        public string surname;
        public string name;
        public int phoneNumber;
        public int[] dateOfBirth;
        public Note(string surname, string name, int phoneNumber, int[] dateOfBirth)
        {
            this.surname = surname;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.dateOfBirth = dateOfBirth;
        }
    }
    static class Program
    {
        //    /// <summary>
        //    /// Главная точка входа для приложения.
        //    /// </summary>
        //    [STAThread]
        //    static void Main()
        //    {
        //        Application.EnableVisualStyles();
        //        Application.SetCompatibleTextRenderingDefault(false);
        //        Application.Run(new Form1());
        //    }
        static public List<Note> list = new List<Note>();
        [STAThread]
        static void Main(string[] args)
        {
            /*try
            {
                List<Note> list = new List<Note>();
                list.Add(new Note("Shilyaev", "Ivan", 1234567, new int[] { 20, 1, 2001 }));
                list.Add(new Note("Berkovich", "Pavel", 9876543, new int[] { 17, 10, 2000 }));
                //list.Add(addElement()); //1
                readFromFile("/Users/ivansilaev/Desktop/input.txt", ref list); //2
                list.Sort(sortBySurname); //4
                writeToFile("/Users/ivansilaev/Desktop/output.txt", ref list); //3
                Note n = searchBySurname("Berkovich", ref list); //5
                Console.WriteLine("Search by surname function:");
                Console.WriteLine(n.phoneNumber);
                Console.WriteLine("Surnames of people born in Jenuary:"); //6
                bornInJanuary(ref list);
                deleteBySurname("Bochkov", ref list); //7
                Console.WriteLine("List of surnames:");
                for (int i = 0; i < list.Count; ++i)
                {
                    Console.WriteLine(list[i].surname);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error: {0}", e.Message);
            }*/
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }


        public static Note addElement()
        { //1
            Console.WriteLine("Enter your surname, name, phone number and date of birth (day, month and year)");
            string surname = Console.ReadLine();
            string name = Console.ReadLine();
            string buf = Console.ReadLine();
            int phoneNumber = Convert.ToInt32(buf);
            int[] arr = new int[3];
            buf = Console.ReadLine(); arr[0] = Convert.ToInt32(buf);
            buf = Console.ReadLine(); arr[1] = Convert.ToInt32(buf);
            buf = Console.ReadLine(); arr[2] = Convert.ToInt32(buf);
            return new Note(surname, name, phoneNumber, arr);
        }

        // данные в файле хранятся по одному элементу в каждой строке
        public static void readFromFile(string fileName, ref List<Note> list)
        { //2
            /*try
            {*/
                list.Clear();
                using (StreamReader sr = new StreamReader(fileName, System.Text.Encoding.Default))
                    for (; ; )
                    {
                        string surname = sr.ReadLine();
                        if (surname == null) { break; }
                        string name = sr.ReadLine();
                        string buf = sr.ReadLine();
                        int phoneNumber = Convert.ToInt32(buf);
                        int[] arr = new int[3];
                        buf = sr.ReadLine(); arr[0] = Convert.ToInt32(buf);
                        buf = sr.ReadLine(); arr[1] = Convert.ToInt32(buf);
                        buf = sr.ReadLine(); arr[2] = Convert.ToInt32(buf);
                        list.Add(new Note(surname, name, phoneNumber, arr));
                    }
            //}
            /*catch (Exception e)
            {
                Console.WriteLine("Error while reading file {0}: {1}", fileName, e.Message);
            }*/
        }

        public static void writeToFile(string fileName, List<Note> list)
        { //3
            using (StreamWriter sw = new StreamWriter(fileName))
            {
                for (int i = 0; i < list.Count; ++i)
                {
                    sw.WriteLine(list[i].surname);
                    sw.WriteLine(list[i].name);
                    sw.WriteLine(list[i].phoneNumber);
                    sw.WriteLine(list[i].dateOfBirth[0]);
                    sw.WriteLine(list[i].dateOfBirth[1]);
                    sw.WriteLine(list[i].dateOfBirth[2]);
                }
            }
        }

        public static int sortBySurname(Note x, Note y)
        { //4
            return String.Compare(x.surname, y.surname);
        }

        public static Note searchBySurname(string surname, ref List<Note> list)
        { //5
            for (int i = 0; i < list.Count; ++i)
            {
                if (list[i].surname.Equals(surname))
                {
                    return list[i];
                }
            }
            throw new Exception("No note with such surname");
        }

        public static void bornInJanuary(ref List<Note> list)
        { //6
            for (int i = 0; i < list.Count; ++i)
            {
                if (list[i].dateOfBirth[1] == 1)
                {
                    Console.WriteLine(list[i].surname);
                }
            }
        }

        public static void deleteBySurname(string surname, ref List<Note> list)
        {
            for (int i = 0; i < list.Count; ++i)
            {
                if (list[i].surname.Equals(surname))
                {
                    list.RemoveAt(i);
                }
            }
        }
    }
}
