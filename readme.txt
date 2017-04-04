Tugas Geofencing.

Disini saya menggunakan base dari yang dikerjakan dikelas mengenai update lokasi yang akurat. Setiap interval waktu tertentu akan di update soal lokasi. Disini saya menggunakan 4 kordinat di setiap sudut gedung GIK yang akan menjadi acuan geofencing disini, jadi ketika kita masuk ke dalam gedung gik, kita update toast akan menampilkan pesan "Anda berada dalam GIK". Kita membandingkan angka Latitude dan Longitude posisi terbaru yang didapet dari gps handphone kita dengan Angka Latitude dan Longitude dari 4 titik gik. Sebagai contoh :

-6.860363, 107.589644 Latitude dan Longitude GIK di kiri bawah
-6.860195, 107.589644 Latitude dan Longitude GIK di kiri atas
-6.860363, 107.590127 Latitude dan Longitude GIK di kanan bawah
-6.860195, 107.590127 Latitude dan Longitude GIK di kanan atas

-6.860387, 107.589886 Latitude dan Longitude di diluar GIK
-6.860280, 107.589757 Latitude dan Longitude di didalam GIK.