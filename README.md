# Perintah Dasar Docker

## ✅ Install Docker

Install Docker bisa menggunakan 2 cara:
1. Install Docker Desktop (Mac, Windows, Linux): https://docs.docker.com/get-started/get-docker/
2. Install Docker Engine di Linux atau WSL: https://docs.docker.com/engine/install/ 

## ✅ Cek Versi

> Docker memerlukan akses administrator untuk eksekusi perintah `docker` sistem Linux. Agar bisa digunakan tanpa akses administrator, masukan user ke dalam group docker dengan perintah `sudo usermod -aG docker $USER`

```
docker version
```

## ✅ Docker Command Help

```
docker --help
```

atau

```
docker -h
```

atau untuk command spesifik

```
docker image --help
```

## 1️⃣ Docker Images

### ✅ Melihat Daftar Docker Image

```
docker image ls
```

### ✅ Download Image Docker

Cari docker image di https://hub.docker.com atau search dengan perintah:

```
docker search <nama_image>
```

Download / pull image

```
docker image pull <name_space>/<nama_image>:<tag>
```

> `<name_space>` biasanya sama dengan username dari pembuat image, berupa alamat kumpulan dari image-image yang dipublish oleh pembuaat image tersebut. Jika melakukan pulling dari official image maka tidak perlu menyertakan `<name_space>`.

Contoh pull image database MariaDB

```
docker pull mariadb:latest
```

> Secara default docker akan melakukan pull dari repository [docker hub](https://hub.docker.com/) milik Docker. Kita dapat mengganti reposity tersebut dengan repository lain dengan perintah lengkap `docker image pull  <DockerRegistry>/<namespace>/<image>:<tag>`

### ✅ Menghapus Image

```
docker image rm <nama_image>:<tag>
```

### ✅ Docker Image Layer

![Docker Imagge Layer](https://miro.medium.com/v2/resize:fit:1400/0*HoxAZTZ2b2C7AM--)

Docker Image dibangun dari beberapa layer yang masing-masing layer tersebut bersifat read only. Masing-masing layer menumpuk satu sama lain, yang kemudian menjadi satu entitas Image. Dengan konsep layering memungkinkan docker meng-caching (cacheable) layer tersebut yang nantinya dapat digunakan kembali (reusable) pada image yang berbeda. Hal ini akan mempercepat proses build dan menghemat penyimpanan pada sistem komputer kita.

Contoh melakukan pull image

```
$ docker image pull nginx:latest

latest: Pulling from library/nginx
6e909acdb790: Already exists 
5eaa34f5b9c2: Pull complete 
417c4bccf534: Pull complete 
e7e0ca015e55: Pull complete 
373fe654e984: Pull complete 
97f5c0f51d43: Pull complete 
c22eb46e871a: Pull complete 
Digest: sha256:124b44bfc9ccd1f3cedf4b592d4d1e8bddb78b51ec2ed5056c52d3692baebc19
Status: Downloaded newer image for nginx:latest
docker.io/library/nginx:latest
```

Kalau kita perhatikan docker mendownload beberapa layer kemudian membuat image.

### ✅ Mengetahui Informasi dalam Image

Perintah `docker image inspect` digunakan untuk menampilkan informasi detail dari satu image atau lebih.

```
$ docker image inspect <nama_image>:<tag>
```

### ✅ Docker Image History

Perintah `docker image history` digunakan untruk melihat history dari suatu image.

```
docker image history <nama_image>:<tag> 
```

## 2️⃣ Docker Container

Container adalah instance dari image. Kita bisa membuat lebih dari satu container dari image yang sama. Container tersebut saling terisolasi sehingga tidak akan bentrok aplikasinya.

![Docker Container Lifecycle](https://blog.techiescamp.com/content/images/2024/05/dockerlifecycle-2.gif)

### ✅ Melihat semua Container baik yang running atau stoped

```
docker container ls -a
```

### ✅ Melihat semua Container yang running saja

```
docker container ls
```

atau

```
docker ps
```

### ✅ Membuat Container

```
docker container create --name <nama_container> <nama_image>:<tag>
```

Contoh:

```
docker container create --name contohhttpd httpd:latest
```

### ✅ Menjalankan Container

```
docker container start contohhttpd
```

atau kita bisa membuat container sekaligus menjalankannya. Contoh menjalankan container `nginx:stable`:

```
docker container run -p 8080:80 nginx:stable
```

Contoh mennjalankan container `nginx:stable` baru dengan beberapa properti (cek properti dengan perintah `--help`):

```
docker run -d -p 8081:80 --name=nginx2 nginx:stable
```

Menjalankan container sekaligus menghapusnya setelah selesai:

```
docker container run --rm <nama_image>:<tag>
```

### ✅ Menghentikan Container

```
docker container stop contohhttpd
```

atau

```
docker container stop <container_id>
```

atau

```
docker container kill <container_id>
```

> Perbedaan `kill` dan `stop`
> * `docker container stop`
>   * sinyal yang dikirim adalah SIGTERM yang dimana container akan melakukan shutdown dan melakukan terminate semua prosesnya secara normal.
>   * Jika dalam 10 detik container tersebut belum mati, maka sinyal yang akan dikirim adalah SIGKILL, yang akan memaksa container mati seketika itu juga.
> * `docker container kill`
>   * Sinyal yang dikirim langsung SIGKILL, yang artinya container akan dipaksa mati seketika itu juga.

### ✅ Menghapus Container

```
docker container rm contohhttpd
```

atau

```
docker container rm <container_id>
```

> Untuk menghapus container harus dalam keadaan stop. Jika ingin menghapus container dalam keadaan running gunakan perintah 

### ✅ Melihat Log

```
docker container logs contohtomcat
```

Melihat Log Realtime

```
docker container logs -f contohtomcat
```

### ✅ Docker Container Attach

Fungsi Docker container attach adalah melampirkan input, output, dan error standar terminal ke kontainer yang sedang berjalan. Dengan begitu, Anda bisa melihat output atau mengendalikan kontainer secara interaktif. 

```
docker container attach <nama_container>
```

Dengan melakukan attach kita bisa langsung melihat log container secara realtime.

### ✅ Docker Container Exec

Docker Container Exec digunakan untuk menjalankan perintah pada container yang sedang berjalan.

Contoh menjalankan perintah `ls` untuk melihat file dan folder dalam container `nginx`

```
docker container exec <nama_container> ls
```

Contoh lain menggunakan option `-i` `-t` (baca `--help`) masuk ke dalam perintah `bash` container:

```
docker container exec -it <nama_container> bash
```

### ✅ Copy file ke dalam Container

Contoh kita akan copy file dari local ke dalam container dengan path `/usr/share/nginx/html/`

```
docker container cp <nama_file> <nama_container>:/usr/share/nginx/html/
```

## 3️⃣ Docker File

### ✅ Membuat Docker File

Dockerfile adalah file teks yang berisi instruksi untuk membuat objek kontainer. File docker file bernama `Dockerfile`. Contoh isi docker file:

```docker
FROM ubuntu:24.04
RUN apt-get update && apt-get install curl -y
CMD ["/usr/bin/curl", "http://timposulabs.com"]
```

Keterangan: 

1. Menjalankan/pull image `ubuntu:24.04`
2. Menjalankan `apt-get update && apt-get install curl -y` 
3. Mejalankan perintah `/usr/bin/curl http://timposulabs.com`

### ✅ Instruksi `FROM`, `LABEL`

* __FROM__ = untuk menentukan base image.

Contoh menggunakan base image Ubuntu 24.04.

```docker
FROM ubuntu:24.04
```

Jika kita tidak menggunakan base image, gunakan perintah `scratch`. Perintah `scratch` akan memberitahu docker bahwa kita akan membangun image dari 0.

```docker
FROM scratch
```

* __LABEL__: untuk memberikan nama atau penanda pada image yang dibuat. Label dibuat menggunakan `key` dan `value`.

```docker
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
```

Contoh melihat isi lengkap `Dockerfile`:

```docker
FROM ubuntu:24.04
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
```

### ✅ Instruksi `ARG`, `ENV`

* __ARG__ = untuk mendeklarasikan variable sekaligus valuenya.

```docker
ARG ubuntu_version=24.04
FROM ubuntu:${ubuntu_version}
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
```

* __ENV__ : untuk mengatur environtent variable pada container.

```docker
ARG ubuntu_version=24.04
FROM ubuntu:${ubuntu_version}
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
ENV DATABASE_NAME=book_store
```

### ✅ Instruksi `CMD`

Instruksi `CMD` digunakan untuk menjalankan atau mendefinisikan proses default atau proses utama ketika proses container dijalankan.

```docker
ARG ubuntu_version=24.04
FROM ubuntu:${ubuntu_version}
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
ENV DATABASE_NAME=book_store
CMD ["/usr/bin/echo", "Halo Bro"]
```

### ✅ Instruksi `RUN`

Instruksi `RUN` hampir sama dengan instruksi `CMD`, cuma perbedaannya jika instruksi `CMD` perintah di dalamnya dieksekusi ketika container dijalankan, instruksi `RUN` dijalankan ketika proses build berjalan. Perbedaan berikutnya instruksi `CMD` digunakan untuk menjalankan atau mendefinisikan proses default atau proses utama ketika proses container dijalankan, konsekuensinya di dalam dockerfile tidak boleh lebih dari satu instruksi `CMD`. Hal tersebut tidak berlaku pada instruksi `RUN` kita dapat mendefinisikan lebih dari satu instruksi `RUN`, __namun__ yang perlu diperhatikan setiap satu instruksi `RUN` akan menammbahkan satu layer dalam suatu image, sehingga diusahakan menggunakan instruksi `RUN` seefisien mungkin.

Contoh kita menambakan instalasi Openjdk JRE 21 pada instruksi `RUN` (dieksekusi pada proses build) pada dockerfile dan pada instruksi `CMD` mengeksekusi `java --version` (dieksekusi ketika container berjalan).

```docker
ARG ubuntu_version=22.04
FROM ubuntu:${ubuntu_version}
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
ENV DATABASE_NAME=book_store
RUN apt-get update && apt-get install openjdk-21-jre-headless -y

CMD ["/usr/bin/java", "--version"]
```

Contoh melakukan build:

> Materi build ada di materi selanjutnya.

```
docker image build -t topekox/first-dockerfile:0.0.3 .
```
### ✅ Instruksi `COPY`

Instruksi `COPY` digunakan untuk menyalin file ke dalam image melalui dockerfile.

Misalnya kita memiliki sebuah file Java sebagai berikut:

```java
class Hello {

	public static void main(String... args) {
		System.out.println("Halo Bro");
	}
}
```

Kemudian melakukan compile yang akan menghasilkan file binary `Hello.class`. File tersebut akan kita copy melalui docker file:

```docker
ARG ubuntu_version=22.04
FROM ubuntu:${ubuntu_version}
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
ENV DATABASE_NAME=book_store
RUN apt-get update && apt-get install openjdk-21-jre-headless -y
COPY Hello.class Hello.class
CMD ["/usr/bin/java", "Hello"]
```

* Build

```
docker image build -t topekox/first-dockerfile:0.0.4 .
```

* Menjalankan container sekaligus menghapusnya setelah selesai:

```
docker container run -it --rm topekox/first-dockerfile:0.0.4
```

* Output

```
Halo Bro
```

### ✅ Instruksi `ADD`

Instruksi `ADD` memiliki fungsi yang hampir sama dengan instruksi `COPY`, yaitu mencopy file/direktori dari host ke dalam image.

```
ARG ubuntu_version=22.04
FROM ubuntu:${ubuntu_version}
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environtment="development"
ENV DATABASE_NAME=book_store
RUN apt-get update && apt-get install openjdk-21-jre-headless -y
ADD Hello.class Hello.class
CMD ["/usr/bin/java", "Hello"]
```

Selain itu instruksi `ADD` memiliki fungsi:

* ✔️ __URL Handling__ = memungkinkan user mengunduh file melalui jaringan internet.
* ✔️ __Archive File Extraction__ = user dapat mengekstract file archive misal `zip` atau `tar`.
* ✔️ __Regular Expression Handling__ = user dapat mencopy file yang menggunakan nama file pattern tertentu.

> Contoh kita akan membuat image dengan ADD file Alpine Linux.

* Download Alpine Linux

```
wget https://dl-cdn.alpinelinux.org/alpine/v3.21/releases/x86_64/alpine-minirootfs-3.21.3-x86_64.tar.gz
```

* Buat `Dockerfile` dan melakukan instruksi `ADD` dengan menambahkan file archive Alpine Linux yang sudah di download.

```docker
FROM scratch
ADD *.tar.gz /
CMD ["/bin/echo", "Hello Alpine Linux"]
```

* Build

```
docker image build -t topekox/my-alpine-linux .
```

* Buat Container

```
docker container run -it --rm topekox/my-alpine-linux
```

Jika ingin masuk ke dalam file sistem container yang sudah dibuat tambahkan perintah `sh`:

```
docker container run -it --rm topekox/my-alpine-linux sh
```

### ✅ Instruksi `WORKDIR`

`WORKDIR` berfungsi untuk mengatur direktori kerja untuk instruksi selanjutnya seperti `RUN`, `CMD`, `ENTRYPOINT`, `COPY` dan `ADD` di dalam `Dockerfile`. Jika `WORKDIR` belum dibuat maka docker dsecara otomatis akan membuat direktori tersebut walaupun tidak pernah digunakan dengan default direktori kerja `/`. Tetapi jika kita tidak membuat `Dockerfile` dari scratch, `WORKDIR` munking berada sesuai base image yang dibuat. Maka untuk menghindari operasi yang tidak diinginkan, best practice-nya kita melakukan instruksi `WORKDIR`.

__Contoh:__

* Buat file `hello.sh`

```sh
#!/bin/bash
echo "Halo Bro!!!"
```

* Buat `Dockerfile`

```docker
FROM alpine:3.21.3
COPY hello.sh entrypoint.sh
CMD ["/bin/sh" , "entrypoint.sh"]
```

Kita mengcopy file `hello.sh` menjadi `entrypoin.sh`.

* Build

```
docker image build -t topekox/workdir-demo:0.0.1 .
```

* Jalankan Container

```
docker container run --rm -it topekox/workdir-demo:0.0.1 sh
```

Maka kita akan masuk ke dalam file sistem image dimana file `entrypoint.sh` berada di working direktori `/`.

__Mengubah Working Direktori__

* Contoh kita mengganti working direktori menjadi direktori `/work`, maka ubah `Dockerfile`menjadi:

```docker
FROM alpine:3.21.3
WORKDIR /work
COPY hello.sh entrypoint.sh
CMD ["/bin/sh" , "entrypoint.sh"]
```

* Lakukan Build

```
docker image build -t topekox/workdir-demo:0.0.2 .
```

* Run Container:

```
docker container run --rm -it topekox/workdir-demo:0.0.2 sh
```

Maka kita akan masuk ke dalam file sistem image dimana file `entrypoint.sh` berada di working direktori `/work`.

### ✅ Instruksi `ENTRYPOINT`

Instruksi `ENTRYPOINT` mirip dengan instruksi `CMD`. Yang membedakan `CMD` akan mengoveride terhadap proses default apa yang akan berjalan (sesuai instruksi `CMD` pada docker file). 

__Contoh `CMD`__

```docker
FROM alpine:3.21.3
WORKDIR /work
COPY hello.sh entrypoint.sh
CMD ["/bin/sh" , "entrypoint.sh"]
```

Jika kita menjalankan container dari image di atas misal dengan mengoverride default proses mencetak pada file `entrypoint.sh` sehingga tidak menampilkan pesan didalamnya:

```
$ docker container run --rm -it topekox/demo-cmd:0.0.1 sh

/work # 
```

__Menggunakan `ENTRYPOINT`__

```docker
FROM alpine:3.21.3
WORKDIR /work
COPY hello.sh entrypoint.sh
ENTRYPOINT ["/bin/sh" , "entrypoint.sh"]
```

Walaupun kita mengoveride proses yang akan berjalan dalam hal ini mencetak dalam file `entrypoint.sh`, contoh menggunakan `sh`, maka tetap akan di eksekusi prosesnya.

```
$ docker container run --rm -it topekox/demo-entrypoint:0.0.1 sh

Halo Bro!!!
```

### ✅ Instruksi `USER`

Secara default docker engine akan menggunakan user root sebagai user dari container, yang secara best practice sebaiknya tidak menggunakan user root. Maka dalam hal ini diperlukan instruksi `USER`.

* Contoh kita mempunyai file `demoping.sh`:

```sh
#!/bin/bash

ping google.com
```

* Kemudian `Dockerfile`:

```docker
FROM alpine:3.21.3
WORKDIR /work
RUN addgroup -S ucup && adduser -S ucup -G ucup  \
	&& chown ucup /work \
	&& chmod -R g+rwx /work

COPY --chown=ucup:topekox *.sh /work
USER ucup
CMD ["/bin/sh" , "demoping.sh"]
```

Instruksi di atas kita membuat `WORKDIR` pada `/work` dan membuat group `topekox` dan user `ucup` kemudian memberikan kepemilikan direktori `/work` pada user `ucup` dan memberikan full akses modifikasi pada group `topekox`, dan memberitahu docker bahwa user adalah `ucup`.

> __Note__: perintah membuat user/group `addgroup` dan `adduser` mungkin berbeda pada distro linux yang lain.

* Kemudian build:

```
docker image build -t topekox/demo-user:0.0.1 .
```

* Run container

```
docker container run --rm -it topekox/demo-user:0.0.1 

PING google.com (216.239.38.120): 56 data bytes
64 bytes from 216.239.38.120: seq=0 ttl=42 time=44.479 ms
64 bytes from 216.239.38.120: seq=1 ttl=42 time=44.346 ms
64 bytes from 216.239.38.120: seq=2 ttl=42 time=44.058 ms
64 bytes from 216.239.38.120: seq=3 ttl=42 time=44.122 ms
```

* Cek dalam container

```
docker container exec -it <nama_container> sh

/work $ ls -la
total 4
drwxrwxr-x    1 ucup     root            22 Mar 25 18:23 .
drwxr-xr-x    1 root     root             8 Mar 25 18:27 ..
-rw-r--r--    1 ucup     ucup            29 Mar 25 17:55 demoping.sh
```

Kita dapat melihat file `demoping.sh` dimiliki oleh user dan group `ucup`.

## 4️⃣ Docker Build

* Cek help

```
docker image build --help
```

* Build

Membuat build dengan berdasarkan user yang ada di [docker hub](https://hub.docker.com/) dengan nama image `first-image` pada direktory yang sama.

```
docker image build -t topekox/first-image:1.0.0 .
```

* Cek docker image

```
docker image ls
```

* Jalankan

```
docker container run topekox/first-image:1.0.0
```

* Menjalankan container sekaligus menghapus dan menggunakan command cli:

```
docker container run -it --rm <nama_image>:<tag>
```

## 5️⃣ Push Image ke Repository

### ✅ Login

```
docker login
```

### ✅ Push Image

Help

```
docker push --help
```

atau

```
docker image push --help
```

Push

```
docker image push topekox/first-image:1.0.0
```
