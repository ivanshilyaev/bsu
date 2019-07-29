using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace laba_3
{
    public partial class Form2 : Form
    {
        public Form2()
        {
            InitializeComponent();
        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            
        }

        private void listBox2_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (listBox1.SelectedItem.ToString().Equals("Red"))
            {
                Form1.isRed = true;
                Form1.isGreen = false;
                Form1.isBlue = false;
            }
            else if (listBox1.SelectedItem.ToString().Equals("Green"))
            {
                Form1.isRed = false;
                Form1.isGreen = true;
                Form1.isBlue = false;
            }
            else if (listBox1.SelectedItem.ToString().Equals("Blue"))
            {
                Form1.isRed = false;
                Form1.isGreen = false;
                Form1.isBlue = true;
            }



            if (listBox2.SelectedItem.ToString().Equals("sin(x)"))
            {
                Form1.isF1 = true;
                Form1.isF2 = false;
                Form1.isF3 = false;
                Form1.isF4 = false;
            }
            else if (listBox2.SelectedItem.ToString().Equals("sin(x+pi/4)"))
            {
                Form1.isF1 = false;
                Form1.isF2 = true;
                Form1.isF3 = false;
                Form1.isF4 = false;
            }
            else if (listBox2.SelectedItem.ToString().Equals("cos(x)"))
            {
                Form1.isF1 = false;
                Form1.isF2 = false;
                Form1.isF3 = true;
                Form1.isF4 = false;
            }
            else if (listBox2.SelectedItem.ToString().Equals("cos(x-pi/4)"))
            {
                Form1.isF1 = false;
                Form1.isF2 = false;
                Form1.isF3 = false;
                Form1.isF4 = true;
            }
            Close();
        }
    }
}
