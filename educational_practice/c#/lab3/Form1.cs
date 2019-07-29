using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace laba_3
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        public float F1(float x)
        {
            return 10*(float)Math.Sin((double)x);
        }

        public float F2(float x)
        {
            return (float)Math.Sin((double)x+Math.PI/4);
        }

        public float F3(float x)
        {
            return (float)Math.Cos((double)x);
        }

        public float F4(float x)
        {
            return (float)Math.Cos((double)x-Math.PI/4);
        }

        public static bool isF1 = false;
        public static bool isF2 = false;
        public static bool isF3 = false;
        public static bool isF4 = false;

        public static bool isRed = false;
        public static bool isGreen = false;
        public static bool isBlue = false;

        private void Form1_Load(object sender, EventArgs e)
        {
            // FormBorderStyle = FormBorderStyle.FixedSingle;  // disable resize
        }

        private Bitmap MakeGraph()
        {
            float xmin = -5;
            float xmax = 5;
            float ymin = -5;
            float ymax = 5;

            // Make the Bitmap.
            int wid = pictureBox1.ClientSize.Width;
            int hgt = pictureBox1.ClientSize.Height;
            Bitmap bm = new Bitmap(wid, hgt);
            using (Graphics gr = Graphics.FromImage(bm))
            {
                RectangleF rect = new RectangleF(xmin, ymin, xmax - xmin, ymax - ymin);

                PointF[] pts =
                {
                    new PointF(0, hgt),
                    new PointF(wid, hgt),
                    new PointF(0, 0),
                };
                gr.Transform = new Matrix(rect, pts);

                // Draw the graph.
                using (Pen graph_pen = new Pen(Color.Blue, 0))
                {
                    // Draw the axes.
                    gr.DrawLine(graph_pen, xmin, 0, xmax, 0);
                    gr.DrawLine(graph_pen, 0, ymin, 0, ymax);
                    for (int x = (int)xmin; x <= xmax; x++)
                    {
                        gr.DrawLine(graph_pen, x, -0.1f, x, 0.1f);
                    }
                    for (int y = (int)ymin; y <= ymax; y++)
                    {
                        gr.DrawLine(graph_pen, -0.1f, y, 0.1f, y);
                    }

                    if (isRed)
                        graph_pen.Color = Color.Red;
                    else if (isGreen)
                        graph_pen.Color = Color.Green;
                    else if (isBlue)
                        graph_pen.Color = Color.Blue;

                    // See how big 1 pixel is horizontally.
                    Matrix inverse = gr.Transform;
                    inverse.Invert();
                    PointF[] pixel_pts =
                    {
                        new PointF(0, 0),
                        new PointF(1, 0)
                    };
                    inverse.TransformPoints(pixel_pts);
                    float dx = pixel_pts[1].X - pixel_pts[0].X;
                    dx /= 2;

                    // Loop over x values to generate points.
                    List<PointF> points = new List<PointF>();
                    for (float x = xmin; x <= xmax; x += dx)
                    {
                        bool valid_point = false;
                        try
                        {
                            // Get the next point.
                            float y = 0;
                            if (isF1)
                                y = F1(x);
                            else if (isF2)
                                y = F2(x);
                            else if (isF3)
                                y = F3(x);
                            else if (isF4)
                                y = F4(x);

                            // If the slope is reasonable,
                            // this is a valid point.
                            if (points.Count == 0) valid_point = true;
                            else
                            {
                                float dy = y - points[points.Count - 1].Y;
                                if (Math.Abs(dy / dx) < 1000)
                                    valid_point = true;
                            }
                            if (valid_point) points.Add(new PointF(x, y));
                        }
                        catch
                        {
                        }

                        // If the new point is invalid, draw
                        // the points in the latest batch.
                        if (!valid_point)
                        {
                            if (points.Count > 1)
                                gr.DrawLines(graph_pen, points.ToArray());
                            points.Clear();
                        }
                    }

                    // Draw the last batch of points.
                    if (points.Count > 1)
                        gr.DrawLines(graph_pen, points.ToArray());
                }
            }

            return bm;
        }

        private void toolStripMenuItem6_Click(object sender, EventArgs e)
        {
            Form2 form2 = new Form2();
            form2.Show();
        }

        private void toolStripMenuItem7_Click(object sender, EventArgs e)
        {
            pictureBox1.Image = MakeGraph();
            pictureBox1.Refresh();
        }

        private void toolStripMenuItem8_Click(object sender, EventArgs e)
        {
            pictureBox1.Image = null;
            pictureBox1.Refresh();
        }

        private void toolStripMenuItem9_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Ivan Shilyaev, 2019", "About", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void toolStripMenuItem10_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Resize(object sender, EventArgs e)
        {
            

        }
    }
}
