using System;
using System.Collections.Generic;
using System.Linq;
using System.Media;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace laba_4
{
    abstract class Bird
    {
        public string name;
        public SoundPlayer player;
        public Bird(string name)
        {
            this.name = name;
        }
        public void sing()
        {
            player.Play();
        }
    }

    class Сuckoo : Bird // кукушка
    {
        public Сuckoo(string name) : base(name)
        {
            player = new System.Media.SoundPlayer("Cuckoo.wav"); 
        }

        public void fly()
        {

        }
    }

    class Chicken : Bird // курица
    {
        public Chicken(string name) : base(name)
        {
            player = new System.Media.SoundPlayer("Chicken.wav");
        }
    }

    class Hen : Chicken // наседка
    {
        public Hen(string name) : base(name)
        {
            player = new System.Media.SoundPlayer("Hen.wav");
        }
    }

    class Cock : Bird
    {
        public Cock(string name) : base(name)
        {
            player = new System.Media.SoundPlayer("Cock.wav");
        }

    }


    static class Program
    {
        /// <summary>
        /// Главная точка входа для приложения.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}
