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
    public partial class Form3 : Form
    {
        public Form3()
        {
            InitializeComponent();
        }

        private void Button1_Click(object sender, EventArgs e)
        {
            textBox2.Text = "";
            Note result = Program.searchBySurname(textBox1.Text, ref Program.list);
            textBox2.Text = result.name + "\r\n" + result.surname + "\r\n" + result.phoneNumber + "\r\n" + result.dateOfBirth[0] + "." + result.dateOfBirth[1] + "." + result.dateOfBirth[2];
        }
    }
}
