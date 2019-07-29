using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace laba_4
{
    public partial class Form1 : Form
    {

        Сuckoo cuckoo = new Сuckoo("Cuckoo");
        Chicken chicken = new Chicken("Chicken");
        Hen hen = new Hen("Hen");
        Cock cock = new Cock("Cock");

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
        }

        private void button1_Click(object sender, EventArgs e)
        {
            cuckoo.sing();
            pictureBox1.Visible = true;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            chicken.sing();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            hen.sing();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            cock.sing();
        }
    }
}
