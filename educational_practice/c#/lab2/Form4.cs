using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace laba
{
    public partial class Form4 : Form
    {
        public Form4()
        {
            InitializeComponent();
        }

        private void Button1_Click(object sender, EventArgs e)
        {
            string name = textBox1.Text;
            string surname = textBox2.Text;
            int number; int.TryParse(textBox3.Text, out number);
            string[] strDate = textBox4.Text.Split('.');
            int[] date = new int[3];
            for (int i = 0; i < 3; i++)
            {
                int.TryParse(strDate[i], out date[i]);
            }
            Program.list.Add(new Note(surname, name, number, date));
            Close();
        }
    }
}
