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
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }


        private void Button1_Click(object sender, EventArgs e)//load
        {
            try
            {
                Program.readFromFile("input.txt", ref Program.list);
            }
            catch (Exception ex)
            {
                textBox1.Text = ex.Message;
            }
            
        }

        private void Button2_Click(object sender, EventArgs e)//showAll
        {
            textBox1.Text = "";
            for (int i = 0; i < Program.list.Count; i++)
            {
                textBox1.Text += Program.list[i].name + "\r\n" + Program.list[i].surname + "\r\n" + Program.list[i].phoneNumber + "\r\n" + Program.list[i].dateOfBirth[0] + "." + Program.list[i].dateOfBirth[1] + "." + Program.list[i].dateOfBirth[2] + "\r\n\r\n\r\n";
            }
        }

        private void Button3_Click(object sender, EventArgs e)//save
        {
            Program.writeToFile("output.txt", Program.list);
        }

        private void Button4_Click(object sender, EventArgs e)//delete
        {
            Form2 deleting = new Form2();
            deleting.ShowDialog();
        }

        private void Button5_Click(object sender, EventArgs e)//find
        {
            Form3 finding = new Form3();
            finding.ShowDialog();
        }

        private void Button6_Click(object sender, EventArgs e)//sort
        {
            Program.list.Sort(Program.sortBySurname);
        }

        private void Button7_Click(object sender, EventArgs e)//add
        {
            Form4 add = new Form4();
            add.ShowDialog();
        }
    }
}
