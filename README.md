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

### ✅ Mengetahui Informasi dalam Image (Inspect)

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

Contoh mennjalankan container `nginx:stable` baru dengan beberapa properti `-d` (detach) untuk menjalankannya sebagai background (cek properti dengan perintah `--help`):

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

### ✅ Docker Container Inspect

#### ✔️ Contoh Docker Container Inspect

* Kita akan melakukan pull dan menjalankan container Nginx Alpine:

```
docker container run --rm nginx:1.23.3-alpine-slim
```

* Setelah container berjalan kemudian di terminal baru kita melakukan inspect untuk mengetahui informasi dari container yang berjalan:

```
$ docker container ps

CONTAINER ID   IMAGE                      COMMAND                  CREATED          STATUS          PORTS     NAMES
c9c4704598ca   nginx:1.23.3-alpine-slim   "/docker-entrypoint.…"   26 seconds ago   Up 25 seconds   80/tcp    friendly_chandrasekhar
```

Dapat dilihat sedang berjalan container dengan id `c9c4704598ca` dengan nama `friendly_chandrasekhar`. Kita bisa melakukan inspect untuk mengetaui informasi detail dari container dengan perintah `docker container inspect <id_container>`.

Contoh:

```
$ docker container inspect c9c4704598ca

[
    {
        "Id": "c9c4704598ca925d609d7e293098c3948c5de214a64d26cc0a936ce0c8eb5fd3",
        "Created": "2025-03-27T15:01:16.837849041Z",
        "Path": "/docker-entrypoint.sh",
        "Args": [
            "nginx",
            "-g",
            "daemon off;"
        ],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 50471,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2025-03-27T15:01:17.076870388Z",
            "FinishedAt": "0001-01-01T00:00:00Z"
        },
        "Image": "sha256:c590972254923ed7847ab22756bdd8d6a3529a099cb1ea59663daabfa3fd940b",
        "ResolvConfPath": "/var/lib/docker/containers/c9c4704598ca925d609d7e293098c3948c5de214a64d26cc0a936ce0c8eb5fd3/resolv.conf",
        "HostnamePath": "/var/lib/docker/containers/c9c4704598ca925d609d7e293098c3948c5de214a64d26cc0a936ce0c8eb5fd3/hostname",
        "HostsPath": "/var/lib/docker/containers/c9c4704598ca925d609d7e293098c3948c5de214a64d26cc0a936ce0c8eb5fd3/hosts",
        "LogPath": "/var/lib/docker/containers/c9c4704598ca925d609d7e293098c3948c5de214a64d26cc0a936ce0c8eb5fd3/c9c4704598ca925d609d7e293098c3948c5de214a64d26cc0a936ce0c8eb5fd3-json.log",
        "Name": "/friendly_chandrasekhar",
        "RestartCount": 0,
        "Driver": "overlay2",
        "Platform": "linux",
        "MountLabel": "",
        "ProcessLabel": "",
        "AppArmorProfile": "",
        "ExecIDs": null,
        "HostConfig": {
            "Binds": null,
            "ContainerIDFile": "",
            "LogConfig": {
                "Type": "json-file",
                "Config": {}
            },
            "NetworkMode": "bridge",
            "PortBindings": {},
            "RestartPolicy": {
                "Name": "no",
                "MaximumRetryCount": 0
            },
            "AutoRemove": true,
            "VolumeDriver": "",
            "VolumesFrom": null,
            "ConsoleSize": [
                32,
                117
            ],
            "CapAdd": null,
            "CapDrop": null,
            "CgroupnsMode": "private",
            "Dns": [],
            "DnsOptions": [],
            "DnsSearch": [],
            "ExtraHosts": null,
            "GroupAdd": null,
            "IpcMode": "private",
            "Cgroup": "",
            "Links": null,
            "OomScoreAdj": 0,
            "PidMode": "",
            "Privileged": false,
            "PublishAllPorts": false,
            "ReadonlyRootfs": false,
            "SecurityOpt": null,
            "UTSMode": "",
            "UsernsMode": "",
            "ShmSize": 67108864,
            "Runtime": "runc",
            "Isolation": "",
            "CpuShares": 0,
            "Memory": 0,
            "NanoCpus": 0,
            "CgroupParent": "",
            "BlkioWeight": 0,
            "BlkioWeightDevice": [],
            "BlkioDeviceReadBps": [],
            "BlkioDeviceWriteBps": [],
            "BlkioDeviceReadIOps": [],
            "BlkioDeviceWriteIOps": [],
            "CpuPeriod": 0,
            "CpuQuota": 0,
            "CpuRealtimePeriod": 0,
            "CpuRealtimeRuntime": 0,
            "CpusetCpus": "",
            "CpusetMems": "",
            "Devices": [],
            "DeviceCgroupRules": null,
            "DeviceRequests": null,
            "MemoryReservation": 0,
            "MemorySwap": 0,
            "MemorySwappiness": null,
            "OomKillDisable": null,
            "PidsLimit": null,
            "Ulimits": [],
            "CpuCount": 0,
            "CpuPercent": 0,
            "IOMaximumIOps": 0,
            "IOMaximumBandwidth": 0,
            "MaskedPaths": [
                "/proc/asound",
                "/proc/acpi",
                "/proc/interrupts",
                "/proc/kcore",
                "/proc/keys",
                "/proc/latency_stats",
                "/proc/timer_list",
                "/proc/timer_stats",
                "/proc/sched_debug",
                "/proc/scsi",
                "/sys/firmware",
                "/sys/devices/virtual/powercap"
            ],
            "ReadonlyPaths": [
                "/proc/bus",
                "/proc/fs",
                "/proc/irq",
                "/proc/sys",
                "/proc/sysrq-trigger"
            ]
        },
        "GraphDriver": {
            "Data": {
                "ID": "c9c4704598ca925d609d7e293098c3948c5de214a64d26cc0a936ce0c8eb5fd3",
                "LowerDir": "/var/lib/docker/overlay2/49fc4bd96363c6f739ca2f35ae2087f53093d07fb4956fd66a5df7a9527171e0-init/diff:/var/lib/docker/overlay2/9e61f8870fb9aa944cab72faa74c67d1f1acfee546adf9fd4334a8d7b711dc8b/diff:/var/lib/docker/overlay2/1a2df0c47fa91619d00d07a216e7bcfc52b1f6650e7cdf3dc4b13e9ff117e925/diff:/var/lib/docker/overlay2/934aa84f0b5a4d186097a4288bd705eddcc01d62792cdfd65e0b8648738bb434/diff:/var/lib/docker/overlay2/284e42141dca97dca4dc8a5f88fcf8bdf9f9266ec05e911ba6beb982786c4ab7/diff:/var/lib/docker/overlay2/6adbd606ecd346cd917f0a71f88618e48b97cae4e87a04262309a787a708dee8/diff:/var/lib/docker/overlay2/eab1820c1678110ba74bae0106508df5405f213151bb082bbc1fd7308bff271b/diff",
                "MergedDir": "/var/lib/docker/overlay2/49fc4bd96363c6f739ca2f35ae2087f53093d07fb4956fd66a5df7a9527171e0/merged",
                "UpperDir": "/var/lib/docker/overlay2/49fc4bd96363c6f739ca2f35ae2087f53093d07fb4956fd66a5df7a9527171e0/diff",
                "WorkDir": "/var/lib/docker/overlay2/49fc4bd96363c6f739ca2f35ae2087f53093d07fb4956fd66a5df7a9527171e0/work"
            },
            "Name": "overlay2"
        },
        "Mounts": [],
        "Config": {
            "Hostname": "c9c4704598ca",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": true,
            "AttachStderr": true,
            "ExposedPorts": {
                "80/tcp": {}
            },
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
                "NGINX_VERSION=1.23.3",
                "PKG_RELEASE=1"
            ],
            "Cmd": [
                "nginx",
                "-g",
                "daemon off;"
            ],
            "Image": "nginx:1.23.3-alpine-slim",
            "Volumes": null,
            "WorkingDir": "",
            "Entrypoint": [
                "/docker-entrypoint.sh"
            ],
            "OnBuild": null,
            "Labels": {
                "maintainer": "NGINX Docker Maintainers <docker-maint@nginx.com>"
            },
            "StopSignal": "SIGQUIT"
        },
        "NetworkSettings": {
            "Bridge": "",
            "SandboxID": "d103834c3b9694a455f5a0304ac5ca828409e7abee43a90b1d805bc40ae9e57a",
            "SandboxKey": "/var/run/docker/netns/d103834c3b96",
            "Ports": {
                "80/tcp": null
            },
            "HairpinMode": false,
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "EndpointID": "e3803e8589f68e9c720f2e72ba06e52cda21055b95cf92c845f1c454d706d28b",
            "Gateway": "172.17.0.1",
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "172.17.0.2",
            "IPPrefixLen": 16,
            "IPv6Gateway": "",
            "MacAddress": "8e:f7:27:6b:a9:ef",
            "Networks": {
                "bridge": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": null,
                    "MacAddress": "8e:f7:27:6b:a9:ef",
                    "DriverOpts": null,
                    "GwPriority": 0,
                    "NetworkID": "f439496455ee73efba625f0f6d678306d3be75621a65456c14d7f2f34de4e71f",
                    "EndpointID": "e3803e8589f68e9c720f2e72ba06e52cda21055b95cf92c845f1c454d706d28b",
                    "Gateway": "172.17.0.1",
                    "IPAddress": "172.17.0.2",
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "DNSNames": null
                }
            }
        }
    }
]
```

#### ✔️ Docker Container Inspect Filter

Dalam dunia nyata kita biasanya hanya membutuhkan informasi tertentu saja. Kita dapat melakukan filter terhadap informasi yang dibutuhkan dalam instruksi Inspect.

Contoh kasus dari contoh di atas, misalnya kita hanya membutuhkan Ip Address saja:

```
$ docker container inspect --format='{{.NetworkSettings.IPAddress}}' c9c4704598ca

172.17.0.2
```

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

### ✅ Menjalankan Container secara Imperatif

Docker container secara imperatif adalah proses menjalankan perintah konfigurasi ketika menjalankan container. 

>__Materi ini perlu memahami materi__:
> * [Docker File](#3%EF%B8%8F⃣-docker-file)
> * [Docker Build](#4%EF%B8%8F⃣-docker-build)

Kita dapat melakukan konfigurasi image pada saat container dijalankan dengan melakukan override value pada konfigurasinya.

Untuk melihat parameter konfigurasi:

```
docker container run --help
```

Berikut di bawah ini beberapa contoh menjalankan container secara imperatif:

#### ✔️ Label

Contoh kasus kita memiliki `dockerfile`:

```docker
FROM eclipse-temurin:21-jre
LABEL version="1.0"
LABEL maintainer="Ucup Topekox" email="ucup@gmail.com" environment="development"
ENV DATABASE_NAME=perpustakaan
COPY Hello.class Hello.class
CMD ["java", "Hello"]
```

Kemudian kita build

```
docker image build -t topekox/label-demo:0.0.1 .
```

1. Membuat container dengan nama `without_label`. Container ini memiliki konfigurasi default dari image yang ada:

```
docker container run -it --name="without_label"  topekox/label-demo:0.0.1
```

Jika kita melakukan inspect maka konfigurasi yang muncul adalan default value.

```
$ docker container inspect without_label

...
"Labels": {
                "email": "ucup@gmail.com",
                "environment": "development",
                "maintainer": "Ucup Topekox",
                "org.opencontainers.image.ref.name": "ubuntu",
                "org.opencontainers.image.version": "24.04",
                "version": "1.0"
            }
...
```

2. Membuat container dengan nama `with_label`. Container ini memiliki konfigurasi yang di override dari image yang ada, dengan mengubah value `maintainer` menjadi `timposulabs.com`:

```
docker container run -it --name="with_label" --label="maintainer=timposulabs.com"  topekox/label-demo:0.0.1 
```

Inspect:

```
docker container inspect with_label
```

atau menampilkadn data yang spesifik:

```
docker container inspect with_label --format="{{.Config.Labels}}"
```

Atau

```
docker inspect without_label --format "{{.Config.Labels}}"
```
Output:

```
map[
    email:ucup@gmail.com 
    environment:development 
    maintainer:timposulabs.com 
    org.opencontainers.image.ref.name:ubuntu 
    org.opencontainers.image.version:24.04 
    version:1.0
    ]
```

#### ✔️ Env

Override value `Env`: Contoh kita akan menganti value `DATABASE_NAME` menjadi `book`:

```
docker container run -it --name="with_env" -e "DATABASE_NAME=book" topekox/label-demo:0.0.
```

Inspect:

```
$ docker inspect with_env --format="{{.Config.Env}}"

[
    DATABASE_NAME=book 
    PATH=/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin 
    JAVA_HOME=/opt/java/openjdk 
    LANG=en_US.UTF-8 
    LANGUAGE=en_US:en 
    LC_ALL=en_US.UTF-8 
    JAVA_VERSION=jdk-21.0.6+7
]
```

#### ✔️ Workdir

Opsi `workdir` digunakan untuk mengatur working direktori dan override pengaturan default. 

Cek working direktori:

```
$ docker container run -it --rm topekox/label-demo:0.0.1 pwd

/
```

Working direktori berada pada `/`. Untuk mengganti workdir misalnya ke direktori `/working`:

```
$ docker container run -it --rm --workdir="/working" topekox/label-demo:0.0.1 pwd

/working
```

#### ✔️ User

Opsi ini berguna untuk meng-assign sebuah user untuk menjalankan proses di dalam container.

Contoh sebuah `Dockerfile`:

```docker
FROM ubuntu:24.04
RUN groupadd -r topekox \
	&& useradd -r -g topekox topekox
```

Kemudian build:

```
docker image build -t topekox/user-demo-imperatif:0.0.1 .
```

Jalankan container:

```
$ docker container run -it --rm --user=topekox topekox/user-demo-imperatif:0.0.1 whoami

topekox
```

### ✅ Self Healing Container

Self Healing Container pada docker dapat dicapai dengan pengaturan restart policy, dimana pengaturan restart policy ini dapat konfigurasi secara imperatif dengan perintah `docker container run`. Dengan restart policy ini memungkinkan container melakukan restart ketika dia mati atau mengalami gangguan.

Pada docker pengaturan restart policy ini setidaknya memiliki 4 mode:

| Flag | Description |
| --- | --- |
| `no` | Saat container stop/mati maka container tidak akan melakukan restart otomatis (default) |
| `on-failure` | Container akan melakukan restart jika container mengalami error, atau exit code yang dikirim bernilai selain 0  |
| `always` | Container akan selalu di restart jika container mati, kecuali container dimatikan secara manual oleh user |
| `unless-stopped` | Mirip dengan `always`, perbedaanya container tidak akan direstart jika container dimatikan (manual atau cara yang lain)  |

> **Catatan**: 
> * Restart Policy hanya berpengaruh jika container berhasil berjalan setidaknya 10 detik.
> * Jika container mati secara manual, maka restart polcy akan diabaikan sampai docker daemon mengalami restart atau container secara manual direstart.

1. Contoh membuat docker container dengan restart policy `always`:

```
docker container run -it --name=nginx --restart=always nginx:1.23.3-alpine-slim
```

2. Cek status di tab terminal baru: 

```
$ docker container ps

CONTAINER ID   IMAGE                      COMMAND                  CREATED          STATUS          PORTS     NAMES
ef59b25cac60   nginx:1.23.3-alpine-slim   "/docker-entrypoint.…"   13 seconds ago   Up 13 seconds   80/tcp    nginx
```

Container sudah berjalan lebih dari 10 detik yaitu 13 detik.

3. Kemudian matikan container dengan menekan `Ctrl + C`, kemudian kita cek lagi status containernya:

```
$ docker container ps

CONTAINER ID   IMAGE                      COMMAND                  CREATED          STATUS         PORTS     NAMES
ef59b25cac60   nginx:1.23.3-alpine-slim   "/docker-entrypoint.…"   28 seconds ago   Up 3 seconds   80/tcp    nginx
```

Maka container akan running otomatis dan sudah berjalan selama 3 detik.

Untuk membuktikan kita dapat melihat lognya:

```
$ docker container logs nginx 

...
2025/03/30 05:50:47 [notice] 1#1: worker process 30 exited with code 0
2025/03/30 05:50:47 [notice] 1#1: exit
/docker-entrypoint.sh: /docker-entrypoint.d/ is not empty, will attempt to perform configuration
...
```

Container sempat mengalami exit status 0.

Jika kita melakukan stop container secara manual kemudian kit melakukan restart daemon / service docker maka container akan berjalan kembali setelah docker service berjalan.

### ✅ Membuat Docker Image dari Container

Membuat Docker Image dari Container biasanya digunakan untuk prototype saja, sehingga jangan digunakan dan tidak recommended.

* Contoh misalnya kita membuat container nginx dengan port `8080`:

```
docker container run -p 8080:80 --name=my-nginx -d nginx:1.23.3-alpine-slim
```

* Cek

```
$ curl localhost:8080


<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
html { color-scheme: light dark; }
body { width: 35em; margin: 0 auto;
font-family: Tahoma, Verdana, Arial, sans-serif; }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>
```

* Contoh misalnya kita mengganti halaman index dari nginx menjadi `index.html`:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Hello World</title>
</head>
<body>
    <h2>Hello World</h2>
</body>
</html>
```

* Copy file di atas ke dalam nginx container:

```
docker container cp index.html my-nginx:/usr/share/nginx/html
```

* Test

```
$ curl localhost:8080


<!DOCTYPE html>
<html>
<head>
    <title>Hello World</title>
</head>
<body>
    <h2>Hello World</h2>
</body>
</html>
```

* Membuild image baru dari container yang ada di atas menggunakan command `commit`:

```
docker container commit --author="Ucup" my-nginx topekox/my-nginx:0.0.1
```

* Menjalankan container dengan port nginx `8081`:

```
docker container run --rm -d -p 8081:80 topekox/my-nginx:0.0.1
```

* Test

```
$ curl localhost:8081


<!DOCTYPE html>
<html>
<head>
    <title>Hello World</title>
</head>
<body>
    <h2>Hello World</h2>
</body>
</html>
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

>__Materi selanjutnya perlu menyelesaikan materi__:
> * [Docker File](#3%EF%B8%8F⃣-docker-file)
> * [Docker Build](#4%EF%B8%8F⃣-docker-build)

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

## 📖 Studi Kasus Spring Boot Docker

Dalam kasus ini kita akan membuat project Spring Boot dengan REST API yang akan mengembalikan response:

```json
{"message":"Halo Bro"}
```

Untuk menjalankan aplikasi di Container ada beberapa yang perlu diperhatikan:

* Perlu dependency (dalam kasus ini JRE)
* Membuat Docker Image
* Menjalankan Container dari Docker Image

1. Membuat Dockerfile pada root project yang akan menjalankan file build spring boot `.jar` yang berada pada direktori `target/`.

```docker
FROM eclipse-temurin:21-jre-ubi9-minimal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
```

2. Build menjadi docker image.

```
 docker image build -t topekox/hello-docker-springboot:0.0.1 .
 ```

 3. Jalankan container dengan port `8080`.

 ```
 docker container run --name=hello-docker -p 8080:8080 topekox/hello-docker-springboot:0.0.1
 ```

4. Testing

```
curl localhost:8080/api/hello

{"message":"Halo Bro"}
```

### 👌 Best Practice

Setelah berhasil membuat docker image dari project Spring Boot app, berikut beberapa best practice yang dapat digunakan:

#### ✅ Menggunakan User non-root

Secara default user yang digunakan dalam docker adalah root, hal ini tidak direkomendasikan dalam dunia security, jadi direkomendasikan menggunakan akses user non-root. 

1. Mengubah dockerfile membuat user `ucup` dan membuat workdir.

```docker
FROM eclipse-temurin:21-jre-ubi9-minimal
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
RUN useradd ucup
USER ucup
ENTRYPOINT [ "java", "-jar", "app.jar" ]
```

2. Build image

```
docker image build -t topekox/hello-docker-springboot:0.0.2 .
```

3. Jalankan container

```
docker container run --name=hello-docker -p 8080:8080 topekox/hello-docker-springboot:0.0.2 
```

4. Masuk ke shell

Buka terminal baru, dan masuk ke dalam mode shell

```
$ docker container exec -it springboot-app sh

sh-5.1$ ls
app.jar
sh-5.1$ pwd
/app
sh-5.1$ whoami
ucup
```

#### ✅ Menggunakan Multistage Build

Docker multi-stage build adalah teknik dalam pembuatan image Docker yang memungkinkan Anda menggunakan beberapa tahap (stage) dalam satu Dockerfile. Setiap tahap dapat menggunakan image dasar (base image) yang berbeda, dan Anda dapat menyalin artefak dari satu tahap ke tahap lainnya.

Contoh kasus sebelumnya kita akan melakukan beberapa stage. Contoh dalam project aplikasi spring boot kita akan membuat container melakukan compile sendiri terhada project spring boot, kemudian di stage berikutnya menjalankan aplikasi spring boot app: 

* Install image JDK.
* Membuat `workdir`.
* Copy file `.mvn` ke dalam image.
* Copy file `mvnw` ke dalam image.
* Copy file `pom.xml` ke dalam image.
* Berikan akses `mvnw` agar bisa dieksekusi.
* Copy `src` ke dalam image.
* Clean project.
* Selanjutnya pada stage berikutnya kita melakukan copy `--from=builder`.

1.Konfigurasi Dockerfile

```docker
FROM eclipse-temurin:21-jdk-ubi9-minimal AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:21-jre-ubi9-minimal
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=builder /app/${JAR_FILE} /app/app.jar
RUN useradd ucup
USER ucup
ENTRYPOINT [ "java", "-jar", "app.jar" ]
```

2. Clean project

```
mvn clean
```

3. Build Image

```
docker image build -t topekox/hello-docker-springboot:multistage-0.0.1 .
```

4. Jalankan Container

```
docker container run --rm --name=spring-boot-app-multistage -p 8080:8080 topekox/hello-docker-springboot:multistage-0.0.1
```

5. Testing

```
curl localhost:8080/api/hello

{"message":"Halo Bro"}
```

#### ✅ Menggunakan Layered `Jar`

Docker Image Layered
![Docker Layer Jar](https://www.baeldung.com/wp-content/uploads/2020/11/docker-layers.jpg)

Docker image menerapkan sistem layering (lihat gambar di atas) dimana layer di bawah adalah Base Image, kemudian layer berikutnya misalnya layer A, kemudian layer B, terus paling atas adalah layer aplikasi kita dalam hal ini aplikasi `.jar`. Kita dapat mengoptimalkan hasil build aplikasi spring boot yang berekstensi `.jar`, dengan cara memecah, atau membagi ke dalam beberapa layer lagi. 

Dalam hal ini jika ada perubahan kode program, kemudian dilakukan proses build, maka untuk layer Base Image sampai Layer B akan aman, karena tidak perlu dibuild ulang (bisa ambil ulang di cache). Sedangkan untuk layer aplikasi jar akan sering dibuild karena sering di ubah. Jadi solusinya layer jar (dalam hal ini aplikasi spring boot) akan dipecah menjadi beberapa layer lagi.

![Spring Boot Layered jar](https://www.baeldung.com/wp-content/uploads/2020/11/spring-boot-layers.jpg)

Spring Boot Layered Jar terdiri dari beberapa layer di antaranya:

* `spring-boot-loader`
* `dependencies`
* `snapshot-dependencies`
* `application`

1. Konfigurasi `Dockerfile`
    * Extract java jar `mvnw clean install && java -Djarmode=layertools -jar target/*.jar extract`.
    * Copy from builder per layer `dependencies`,`spring-boot-loader`, `snapshot-dependencies` dan `application`.
    * Ubah Entrypoint `org.springframework.boot.loader.launch.JarLauncher`.

```docker
FROM eclipse-temurin:21-jdk-ubi9-minimal AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install && java -Djarmode=layertools -jar target/*.jar extract

FROM eclipse-temurin:21-jre-ubi9-minimal
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

RUN useradd ucup
USER ucup
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
```

2. Build

```
docker image build -t topekox/hello-docker-springboot:layered-0.0.1 .
```

3. Jalankan Container

```
docker container run --rm  --name=spring-boot-app-layer -p 8080:8080 topekox/hello-docker-springboot:layered-0.0.1
```

## 6️⃣ Docker Network

Docker memiliki solusi untuk menyediakan fitur networking antar container, bernama **Container Network Model (CNM)**. CNM merupakan standar spesifikasi dan memerlukan implementasi salah satu implementasi tersebut adalah **libnetwork**. Selain itu docker juga memiliki **Driver** untuk jenis topologi jaringan yang berbeda-beda.

![Container Network Model](https://thenewstack.io/wp-content/uploads/2016/09/Chart_Container-Network-Model-Drivers.png)

CNM terdiri 3 block yaitu:
1. **sandbox**: network yang terisolasi dalam container termasuk di dalamnya interface ethernet, port, dns config.
2. **endpoint**: merupakan Virtual Ethernet Interface yang berfugsi membuat koneksi jaringan
3. **network**: merupakan implementasi dari virtual switch yang mengelompokkan dan mengisolasi kumpulan endpoint yang saling berkomunikasi.

![Container Network Model](https://www.dclessons.com/uploads/2019/09/Docker-7.2.png)

Berikut gambaran besar dari Docker Network

![CNN](https://k21academy.com/wp-content/uploads/2020/06/CNN-Model-1.png)

#### ✅ Perintah Dasar

Perintah dasar dari docker network adalah sbb:

```
docker network
```

| Commands | Description |
| --- | --- |
| `connect` |Connect a container to a network |
| `create` | Create a network |
| `disconnect` | Disconnect a container from a network |
| `inspect` | Display detailed information on one or more networks |
| `ls` | List networks |
| `prune` | Remove all unused networks |
| `rm` | Remove one or more networks |


Melihat daftar docker network:

```
docker network ls
```

### ✅ Jenis-jenis Network

Berikut ini adalah jenis type network (__Driver__) dalam docker

* **bridge** : (default) merupakan jaringan jembatan ke sebuah virtual subnet yanh dikonfigurasi pada host (network berbeda dengan network mesin komputer host).
* **host** : Container docker akan memiliki network yang sama dengan network komputer host.
* **none** : Container tidak menggunakan driver network apapun.

#### ✔️ Membuat Network Bridge

Untuk melihat detail dari network bridge jalankan perintah:

```
docker network inspect bridge
```

Dapat dilihat pada bagian `Options`, bahwa default bridge name adalah `docker0`. Nama `docker0` dikonfigurasi dari dari sistem host (kernel linux), yang dapat dilihat dengan perintah `ip add`.

> Untuk dapat melihat daftar bridge di linux kita dapat menginstall `bridge-utils` kemudian jalankan perintah `brctl show`.

#### 1️⃣ Membuat Network Bridge tidak bersama Container

1. Membuat network baru dengan drive bridge dan nama `topekox-network`:

```
docker network create -d bridge topekox-network
```

2. Melihat daftar network:

```
$ docker network ls

22e1f5c81383   bridge            bridge    local
bb6807dc5fdb   host              host      local
9e53f3b177e5   none              null      local
182a2e6f3e5a   topekox-network   bridge    local
```

Terdapat network baru dengan nama `topekox-network`.

![docker network](/img/docker1.png)

Dapat juga mengecek dari sisi kernel linux host:

```
brctl show
ip addr
```

3. Run Container

Misalnya menjalankan container tanpa opsi network:

```
docker container run --rm -d --name=spring-boot-app -p 8080:8080 topekox/hello-docker-springboot:layered-0.0.1 
```

Kemudian kita inspect:

```
docker network inspect bridge
```

Maka pada bagian `Containers` akan dibuat kan container dengan network bridge secara default dengan nama `docker0`.

```json
"Containers": {
    "c5dcaf79e93bce444219d6721b12d7d257aaa9ac1bb68d7deea010ff10f0cd41": {
        "Name": "spring-boot-app",
        "EndpointID": "e80ceb999d3b2e3c11f33413463f837f39720e4cabca64f8068861854a1680dd",
        "MacAddress": "ae:04:85:e9:09:e7",
        "IPv4Address": "172.17.0.2/16",
        "IPv6Address": ""
    }
}
```

4. Menghubungkan app ke network.

Untuk menghubungkan aplikasi spring boot ke network yang sudah dibuat, menggunakan perintah `docker network connect <network_name container_name>`

```
docker network connect topekox-network spring-boot-app
```

Selanjutnya melakukan inspect docker network

```
docker network inspect topekox-network
```
Result:

```json
"Containers": {
    "c5dcaf79e93bce444219d6721b12d7d257aaa9ac1bb68d7deea010ff10f0cd41": {
        "Name": "spring-boot-app",
        "EndpointID": "fdd6c5afca5f168d1367c1010d67f65ed124e5f8105d7825c1de0fd6185fd889",
        "MacAddress": "02:a8:5e:cb:01:b0",
        "IPv4Address": "172.19.0.2/16",
        "IPv6Address": ""
    }
},
```

Maka seharusnya aplikasi spring boot sudah terhubung ke 2 jaringan yaitu `bridge` dan `topekox-network`.

![docker network](/img/docker2.png)

Inspect docker container:

```
docker container inspect spring-boot-app
```

Result:

```json
"Networks": {
    "bridge": {
        "IPAMConfig": null,
        "Links": null,
        "Aliases": null,
        "MacAddress": "ae:04:85:e9:09:e7",
        "DriverOpts": null,
        "GwPriority": 0,
        "NetworkID": "22e1f5c81383f15a86a3ebf568e6e1fb260212deab628d3a6f37b1d876458897",
        "EndpointID": "e80ceb999d3b2e3c11f33413463f837f39720e4cabca64f8068861854a1680dd",
        "Gateway": "172.17.0.1",
        "IPAddress": "172.17.0.2",
        "IPPrefixLen": 16,
        "IPv6Gateway": "",
        "GlobalIPv6Address": "",
        "GlobalIPv6PrefixLen": 0,
        "DNSNames": null
    },
    "topekox-network": {
        "IPAMConfig": {},
        "Links": null,
        "Aliases": [],
        "MacAddress": "02:a8:5e:cb:01:b0",
        "DriverOpts": {},
        "GwPriority": 0,
        "NetworkID": "182a2e6f3e5aeef3b6086df77591af79a5fbe49960ff0111bddffb74a7724bd9",
        "EndpointID": "fdd6c5afca5f168d1367c1010d67f65ed124e5f8105d7825c1de0fd6185fd889",
        "Gateway": "172.19.0.1",
        "IPAddress": "172.19.0.2",
        "IPPrefixLen": 16,
        "IPv6Gateway": "",
        "GlobalIPv6Address": "",
        "GlobalIPv6PrefixLen": 0,
        "DNSNames": [
            "spring-boot-app",
            "c5dcaf79e93b"
        ]
    }
}
```

#### 2️⃣ Membuat Network Bridge dengan Container

Kita akan membuat 3 container dengan network yang sama yaitu container spring boot app, nginx dan busybox.

> busybox adalah software yang menyediakan beberapa utilitas umum (contoh ping) yang biasanya digunakan pada linux dan unix.

![docker networ](/img/docker3.png)

1. Membuat network dengan container

```
docker container run --rm -d --name=spring-boot-app --network=topekox-network topekox/hello-docker-springboot:layered-0.0.1 
```

```
docker container run --rm -d --name=webserver --network=topekox-network -p 80:80 nginx:stable-bullseye
```

```
docker container run --rm -it --name=busybox --network=topekox-network busybox:stable
```

2. Inspect network `topekox-network`

Buka tab terminal baru lalu inspect:

```
docker network inspect topekox-network
```

Terdapat 3 container dalam network:

```json
"Containers": {
    "0949ea482b2f55094cc7a703d8871c529d9486e7c5b68817390d5961d41108a2": {
        "Name": "busybox",
        "EndpointID": "9e0a04fdae47b8562176df71b85ad10ff804d3ab450ce5e7ceeac9ede0e894f8",
        "MacAddress": "be:da:d1:33:41:2e",
        "IPv4Address": "172.19.0.4/16",
        "IPv6Address": ""
    },
    "29441d29ff44cf19e2b3c0a34a939457cc2b06ea59fa9101f15fbe3aea246688": {
        "Name": "webserver",
        "EndpointID": "14f5669f4ef133a28797beff572c7657a7e40ef8d727c398e7f1858f9c06a2ce",
        "MacAddress": "e6:8a:5f:c2:48:c5",
        "IPv4Address": "172.19.0.3/16",
        "IPv6Address": ""
    },
    "91321142f0d007cdefb624d84414fb3fb440f5fea0c4607c397b668c6d99979b": {
        "Name": "spring-boot-app",
        "EndpointID": "0522eda5224975c6431e8fef29197ededd5ed39f79e7be87462db4f653ff9301",
        "MacAddress": "1a:4d:7b:c8:ac:99",
        "IPv4Address": "172.19.0.2/16",
        "IPv6Address": ""
    }
},
```

3. Test network dari busybox dengan `ping`:

```
ping 172.19.0.3

ping 172.19.0.4
```

atau ping dengan nama contrainer

```
ping webserver

ping spring-boot-app
```

cek aplikasi dengan `wget` karena `curl` tidak terdapat pada busybox

```
wget webserver

wget spring-boot-app:8080/api/hello
```

#### 3️⃣ Reverse Proxy dengan Nginx

Kita akan menggunakan reverse proxy nginx dimana di kita memiliki 2 container yaitu:

* Container `nginx`
* Container `spring-boot-app`

Yang mana nantinya request akan melalui container `nginx` terlebih dahulu kemudian di teruskan ke `spring-boot-app` yang berada dalam network yang sama.

![docker reverse proxy](/img/docker4.png)

1. Membuat konfigurasi nginx contoh dengan nama `reverse-proxy.conf`:

```nginx
server {
  listen 80;
  server_name spring-boot-app;

  location / {
    proxy_pass http://spring-boot-app:8080/;    
  }
}
```

2. Membuat dockerfile image dari base image nginx:

```docker
FROM nginx:stable-bullseye
COPY reverse-proxy.conf /etc/nginx/conf.d/default.conf
```

3. Build image docker

```
docker image build -t topekox/nginx:0.0.1 .
```

4. Jalankan Container

* Container Spring Boot App:

```
docker container run -d --rm --name spring-boot-app --network topekox-network topekox/hello-docker-springboot:layered-0.0.1
```

* Container Nginx:

```
docker container run -d --rm --name webserver --network topekox-network -p 80:80 topekox/nginx:0.0.1 
```

5. Cek inspect network:

```
$ docker network inspect topekox-network 

"Containers": {
    "b811a895370099b2081ac50158eeeace68ea10c01284fa5ee4438e4c0df28fb4": {
        "Name": "spring-boot-app",
        "EndpointID": "c7d30afe5729ce236d3bf7b6823e565de34f4692cc302fde2630718e45307cb4",
        "MacAddress": "86:d6:d1:2f:87:6b",
        "IPv4Address": "172.19.0.2/16",
        "IPv6Address": ""
    },
    "e99bd282f05c6e151a996a0a25ad607f96ed09ec5c1509d8799f81af3c83d486": {
        "Name": "webserver",
        "EndpointID": "dbc591ecd6731f57852aeebb9eafe5011a384935f66bdd8419eb2fc5fad4fed0",
        "MacAddress": "4e:aa:b8:9c:dd:f6",
        "IPv4Address": "172.19.0.3/16",
        "IPv6Address": ""
    }
}
```

terdapat 2 container di dalam network `topekox-network`.

6. Test

```
$ curl localhost:80/api/hello

{"message":"Halo Bro"}
```

#### 4️⃣ Load Balancer dengan Nginx

Load balancing merupakan proses pendistribusian traffic atau lalu lintas jaringan secara efisien ke dalam sekelompok server, atau yang lebih dikenal dengan server pool atau server farm. Load balancing ini berguna agar salah satu server dari website yang mendapatkan banyak lalu linta kunjungan tidak mengalami kelebihan beban.

Load balancer akan bekerja dengan cara mendistribusikan lalu lintas kunjungan ke dalam beberapa server demi memastikan tidak ada salah satu server yang mengalami kelebihan beban. Load balancer akan meminimalkan waktu respons server secara efektif.

Contoh ilustrasi:

![docker load balancer](/img/docker-loadbalancer.png)

1. Mengubah aplikasi spring boot untuk menghasilkan REST API sbb:

```json
{
    "ipAddress": "192.168.1.10",
    "message": "Halo Bro",
    "hostname": "timposulabs"
}
```

2. Build image Spring Boot App

```
 docker image build -t topekox/hello-docker-springboot:layered-0.0.2 .
```

Sekarang kita memiliki image dari `hello-docker-springboot:layered-0.0.2`

3. Membuat file `load-balancer.conf`:

```nginx
upstream backend {
  server spring-boot-app-1:8080;
  server spring-boot-app-2:8080;
}

server {
  listen 80;

  location / {
    proxy_pass http://backend;
  }
}
```

4. Membuat `Dockerfile`:

```docker
FROM nginx:stable-bullseye
COPY load-balancer.conf /etc/nginx/conf.d/default.conf
```

5. Membuat Docker Image Nginx:

```
docker image build -t topekox/nginx:0.0.2 .
```

6. Membuat Container:

* Container `spring-boot-app-1`:

```
docker container run -d --rm --name spring-boot-app-1 --network topekox-network topekox/hello-docker-springboot:layered-0.0.2
```

* Container `spring-boot-app-2`:

```
docker container run -d --rm --name spring-boot-app-2 --network topekox-network topekox/hello-docker-springboot:layered-0.0.2
```

* Container web server nginx dengan mapping port `5001`

```
docker container run -d --rm --name webserver --network topekox-network -p 5001:80 topekox/nginx:0.0.2 
```

7. Testing

Ketika kita melakukan ke alamat `http://localhost:5001/api/hello` maka ip addres yang di tampilkan akan berubah-ubah

* Percobaan 1

```json
{
    "hostname": "2d7a34bcb026",
    "message": "Halo Bro",
    "ipAddress": "172.19.0.2"
}
```

* Percobaan 2

```json
{
    "message": "Halo Bro",
    "hostname": "99d567c5dd5d",
    "ipAddress": "172.19.0.3"
}
```

## 7️⃣ Docker Volume

Setiap file sistem container dalam docker bersifat stateless atau berdiri sendiri, sehingga container tidak dirancang persistence atau untuk menyimpan data untuk waktu yang lama sehingga ketika container terhapus maka seluruh data dalam container tersebut akan terhapus juga.

Untuk menyimpan data secara persistence maka dibutuhkan Docker Volume. Docker volume adalah satuan storage penyimpanan yang memiliki lifecycle berbeda dengan container, sehingga apabila container terhapus maka data dalam docker volume tetap ada. Docker volume juga dapat digunakan lebih dari 2 container sehingga dapat digunakan untuk share data antar container.

### ✅ Membuat Docker Volume secara Imperative

Kita dapat membuat docker volume secara imperative atau lansung menggunakan command saat membuat container.

![Docker Volume](/img/docker-volume1.png)

#### Membuat Docker Volume

```
docker volume create topekox-volume
```

Cek daftar volume:

```
docker volume ls

DRIVER    VOLUME NAME
local     topekox-volume
```

#### Membuat Container

Membuat Container busybox dengan nama `busybox` yang terhubung dengan docker volume `topekox-volume` dan mount volume berada di `/data`:

```
docker container run -it --rm -v topekox-volume:/data --name busybox busybox:stable
```

Kita dapat memmbuat file dalam busybox dan menghapus containernya.

```
cd data
/data # touch myfile
/data # ls
myfile
```

#### Melihat data Docker Volume di Host

Untuk melihat lokasi data kita dapat melakukan inspect:

```
$ docker volume inspect topekox-volume 

[
    {
        "CreatedAt": "2025-04-02T22:51:30+08:00",
        "Driver": "local",
        "Labels": null,
        "Mountpoint": "/var/lib/docker/volumes/topekox-volume/_data",
        "Name": "topekox-volume",
        "Options": null,
        "Scope": "local"
    }
]
```

Dari hasil di atas lokasi data di komputer host adalah `/var/lib/docker/volumes/topekox-volume/_data`.

Kita bisa melihat isi datanya dengan akses root komputer host:

```
$ sudo su -
root@timposulabs:~# cd /var/lib/docker/volumes/topekox-volume/_data
root@timposulabs:/var/lib/docker/volumes/topekox-volume/_data# ls
myfile
```

File `myfile` yang dibuat pada container masih ada di docker volume.

### ✅ Berbagi Docker Volume dan Menghapus Volume

![Docker Volume](/img/docker-volume2.png)

#### Share Docker Volume

Disini kita akan membuat 2 docker container busybox dengan nama `busybox1` dan `busybox2`:

* Buat container `busybox1`:

```
docker container run -it --rm -v topekox-volume:/data --name busybox1 busybox:stable
```

* Buka tab baru dan buat container `busybox2`:

```
docker container run -it --rm -v topekox-volume:/data --name busybox2 busybox:stable
```

* Pada `busybox2` kita akan membuat file baru:

```
/ # cd data 
/data # ls
myfile
/data # echo "Halo Bro" > hello
/data # cat hello 
Halo Bro
```

* Pada `busybox1` melakukan pengecekan:

```
/data # ls
myfile
/data # ls
hello   myfile
/data # cat hello 
Halo Bro
```

Dari sini kita sudah berhasil share data pada docker volume.


#### Menghapus Docker Volume

> **Catatan**: Sebelum menghapus volume pastikan container yang menggunakan volume-nya sudah mati.

Untuk menghapus volume contoh nama volume `topekox-volume`:

```
docker volume rm topekox-volume 
```

### ✅ Menggunakan Database PostgreSQL

> Baca dokumentasi resmi docker [PostgreSQL](https://hub.docker.com/_/postgres)

* Buat Container postgres, di sini saya menggunakan Postgres versi 17.4:

```
docker container run -d --name my-postgresql -e POSTGRES_PASSWORD=test123 postgres:17.4
```

* Karena container tidak diekspose ke jaringan luar container kita harus masuk ke dalam container Postgres:

```bash
$ docker exec -it -u postgres my-postgresql psql

psql (17.4 (Debian 17.4-1.pgdg120+2))
Type "help" for help.

postgres=# 
```

Jika kita membuat database di dalam container postgres saat ini, kemudian menghapus container tersebut maka data yang ada di dalam container tersebut akan terhapus juga, karena sifat container yang stateless. Walaupun kita membuat container baru maka database akan kembali baru. 

Untuk keperluan tersebut di atas maka kita harus menambahkan volume ketika membuat container.

* Buat Container postgres, dengan memounting volume nya ke `/var/lib/postgresql/data` (baca dokumentasi) dengan nama volume `postgres-data`:

```
docker container run -d --rm --name my-postgresql -e POSTGRES_PASSWORD=test123 -v postgres-data:/var/lib/postgresql/data postgres:17.4
```

* Masuk ke consol postgre docker:

```
docker exec -it -u postgres my-postgresql psql
```

* Buat Database:

```sql
postgres=# create database school;
CREATE DATABASE
postgres=# \l

Name    |  Owner   | Encoding | Locale Provider |  Collate   |   Ctype    | Locale | ICU Rules |   Access privileges   
-----------+----------+----------+-----------------+------------+------------+--------+-----------+-----------------------
 postgres  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | 
 school    | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | 
 template0 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | =c/postgres          +
           |          |          |                 |            |            |        |           | postgres=CTc/postgres
 template1 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | =c/postgres          +
           |          |          |                 |            |            |        |           | postgres=CTc/postgres
(4 rows)
```

* Untuk mengujinya silahkan keluar bash postgre kemudian hapus container, lalu buat container postgre yang baru masuk ke console postgre dan cek databasenya kembali.

## 📖 Studi Kasus Spring Boot Docker menggunakan Database dan Docker Volume

Kita akan membuat 2 container Spring Boot yang masing-masing menggunakan database postgres dan docker volume dengan gambaran sebagai berikut:

![docker spring boot postgres with volume](/img/docker5.png)

### ✅ Membuat Container Database

1. Membuat Container Database PostgreSQL

```
docker container run -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres --network topekox-network -v topekox-volume:/var/lib/postgresql/data --name postgresql postgres:17.4
```

2. Masuk ke dalam bash container

```
docker container exec -it postgresql bash
```

3. Masuk ke database, dan buat database:

```
root@61418a343f87:/# psql -U postgres

psql (17.4 (Debian 17.4-1.pgdg120+2))
Type "help" for help.

postgres=# create database springbootapp;
CREATE DATABASE
postgres=#
```

4. Membuat Image Spring Boot App

Buat project Spring Boot, contoh project dapat dlihat [di sini](https://github.com/TopekoX/belajar-docker/tree/main/example10/springboot-pagination-dto).

* Application Properties

```yml
spring:
  datasource:
    hikari:
      connection-timeout: 600000
      idle-timeout: 600000
      max-lifetime: 1800000
      maximum-pool-size: 10
    url: jdbc:postgresql://${DATABASE_ADDR:localhost}/${DATABASE_NAME:springbootapp}?reWriteBatchedInserts=true
    username: ${DATABASE_USERNAME:postgres}
    password: ${DATABASE_PASSWORD:postgres}
  jpa:
    database: POSTGRESQL
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        jdbc:
          batch_size: 100
    show-sql: true
```

* Dockerfile

```docker
FROM eclipse-temurin:21-jdk-ubi9-minimal AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean package -Dmaven.test.skip

FROM eclipse-temurin:21-jre-ubi9-minimal AS layered
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} /app/app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-ubi9-minimal
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=layered /app/dependencies/ ./
COPY --from=layered /app/spring-boot-loader/ ./
COPY --from=layered /app/snapshot-dependencies/ ./
COPY --from=layered /app/application/ ./

ENV DATABASE_ADDR=postgresql
ENV DATABASE_NAME=springbootapp
ENV DATABASE_USER=postgres
ENV DATABASE_PASSWORD=postgres

RUN useradd ucup
USER ucup

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
```

5. Build Image contoh dengan nama `topekox/springboot-dto-pagination:0.0.1`.

6. Membuat container `spring-boot-app-1`:

```
docker container run -d --name spring-boot-app-1 -e DATABASE_ADDR=postgresql -e DATABASE_NAME=springbootapp -e DATABASE_USER=postgres -e DATABASE_PASSWORD=postgres --network topekox-network topekox/springboot-dto-pagination:0.0.1
```

> Setelah membuat container, kita dapat memeriksa database di dalam container postgresql, seharusnya sudah dibuatkan tabel oleh spring boot.

7. Membuat container `spring-boot-app-2`:

```
docker container run -d --name spring-boot-app-2 -e DATABASE_ADDR=postgresql -e DATABASE_NAME=springbootapp -e DATABASE_USER=postgres -e DATABASE_PASSWORD=postgres --network topekox-network topekox/springboot-dto-pagination:0.0.1 
```

8. Membuat container nginx:

* `Dockerfile`

```docker
FROM nginx:stable-bullseye
COPY load-balancer.conf /etc/nginx/conf.d/default.conf
```

* `load-balancer.conf`

```
upstream backend {
  server spring-boot-app-1:8080;
  server spring-boot-app-2:8080;
}

server {
  listen 80;

  location / {
    proxy_pass http://backend;
  }
}
```

* buat container

```
docker container run -d --name nginx --network topekox-network -p 80:80 topekox/nginx:0.0.1
```

9. Uji coba dengan menggunakan cURL, postman dll. ke alamat `localhost:80/api/product`

## 8️⃣ Docker Compose

Docker Compose adalah alat yang digunakan untuk mendefinisikan dan menjalankan aplikasi Docker multikontainer. Docker Compose dapat memudahkan pengelolaan layanan yang saling terhubung, seperti frontend, backend API, dan database. 

Docker Compose dapat membantu: Mengotomatisasi eksekusi banyak kontainer dengan satu perintah, Menyatukan berbagai macam Dockerfile menjadi satu file, Menentukan tumpukan aplikasi dalam sebuah file, Memungkinkan orang lain untuk berkontribusi pada proyek, Mengembangkan aplikasi lokal. Docker Compose Menggunakan file YAML untuk mendefinisikan layanan.

> [Dokumentasi Resmi YAML](https://yaml.org/)

Secara default filename docker compose adalah `compose.yaml` atau `compose.yml` selain itu juga bisa `docker-compose.yml` atau `docker-compose.yaml`. Jika kedua file tersebut berada di direktori yang sama maka yang akan docker gunakan adalah `compose.yml`.

> [Dokumentasi Docker Compose](https://docs.docker.com/compose/)

Top Elemen dalam docker compose:

* `services`
* `networks`
* `volumes`
* `configs`
* `secrets`

### ✅ Membuat Docker Compose

* Buat file `compose.yaml`:

```yaml
services:
  nginx:
    image: nginx:stable-bullseye
    ports:
      - "8080:80"
```

* Menjalankan script compose (harus berada di direktori yang sama):

```
docker compose up
```

* Test

```
curl localhost:8080
```

> Secara default nama container atau service dalam docker compose, akan dibuat secara otomatis oleh docker untuk mendefinisikan nama service secara manual tambahkan `container_name: <nama_container>`
>Contoh:
>
> ```yaml
> services:
>   nginx:
>     container_name: webserver
>     image: nginx:stable-bullseye
>     ports:
>       - "8080:80"
> ``` 

### ✅ Docker Compose Up & Down

* `docker compose up` berfungsi untuk mendeploy aplikasi yang berada dalam script compose. Perintah ini akan mengunduh image, membuat container, network dan volume sesuai dengan docker compose.

Untuk mengetahui perintah dalam `docker compose up` menggunakan perintah:

```
docker compose up --help
```

Contoh menjalankan `
Untuk mengetahui perintah dalam `docker compose up --detach` untuk menjalakan docker compose pada background.

* `docker compose down` berfungsi untuk mematikan sekaligus menghapus semua resource yang ada dalam `compose.yaml` baik itu container atau resource network.

```
docker compose down
```

Jika hanya ingin mematikan container saja tanpa mematikan resource network maka gunakan perintah:

```
docker compose stop
```

### ✅ Konfigurasi Network Docker Compose

Kita akan menambahkan elemen network dalam docker compose. Contoh kita akan membuat nework dengan nama `topekox-network`, karena sebelumnya sudah ada network dengan nama tersebut maka kita perlu menambahkan `external:true`.

```yaml
services:
  nginx:
    image: nginx:stable-bullseye
    ports:
      - "8080:80"
networks:
  topekox-networks:
    driver: bridge
    external: true
```

Lakukan `docker compose up` dan untuk mengecek jalankan perintah:

```
$ docker network inspect topekox-network 

"Containers": {
            "502316f437661f97212fa832be10e75f864aad34ffdb4eeaaf3318e71c879ede": {
                "Name": "example11-nginx-1",
                "EndpointID": "25b16c0bcd22b9d6dd90d13e29e21b98710a84cd16eff120e917b8deee2e7529",
                "MacAddress": "6e:93:20:dc:ff:81",
                "IPv4Address": "172.19.0.2/16",
                "IPv6Address": ""
            }
        },
```

### ✅ Konfigurasi Volume Docker Compose

Disini kita akan menambahakan volume dan menambahkan konfigurasi postgresql seperti materi sebelumnya.

* Ubah file `compose.yaml` menambahkan `volume` dan service dan konfigurasi untuk `postgresql`:

```yaml
services:
  nginx:
    image: nginx:stable-bullseye
    ports:
      - "8080:80"
    networks:
      - topekox-network
  postgresql:
    image: postgres:17.4
    environment:
      - POSTGRES_PASSWORD=postgres
    networks:
      - topekox-network
    volumes:
      - topekox-volume:/var/lib/postgresql/data
networks:
  topekox-network:
    driver: bridge
    external: true
volumes:
  topekox-volume:
    driver: local
```

* Kemudian lakukan up:

```
docker compose up
```

* Cek container yang run

```
docker container ls
```

* Masuk ke dalam postresql dan buat database `school`:

```
$ docker container exec -it -u postgres example11-postgresql-1 psql


psql (17.4 (Debian 17.4-1.pgdg120+2))
Type "help" for help.

postgres=# create database school;
CREATE DATABASE
postgres=# \l

   Name    |  Owner   | Encoding | Locale Provider |  Collate   |   Ctype    | Locale | ICU Rules |   Access privileges   
-----------+----------+----------+-----------------+------------+------------+--------+-----------+-----------------------
 postgres  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | 
 school    | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | 
 template0 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | =c/postgres          +
           |          |          |                 |            |            |        |           | postgres=CTc/postgres
 template1 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | =c/postgres          +
           |          |          |                 |            |            |        |           | postgres=CTc/postgres
(4 rows)

postgres=# exit
```

* Compose down untuk menghapus isi dalam resource `compose.yaml` dan kemudian up lagi

```
docker compose down

docker compose up
```


* Cek isi database lagi

```
$ docker container exec -it -u postgres example11-postgresql-1 psql


psql (17.4 (Debian 17.4-1.pgdg120+2))
Type "help" for help.

postgres=# \l

   Name    |  Owner   | Encoding | Locale Provider |  Collate   |   Ctype    | Locale | ICU Rules |   Access privileges   
-----------+----------+----------+-----------------+------------+------------+--------+-----------+-----------------------
 postgres  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | 
 school    | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | 
 template0 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | =c/postgres          +
           |          |          |                 |            |            |        |           | postgres=CTc/postgres
 template1 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | =c/postgres          +
           |          |          |                 |            |            |        |           | postgres=CTc/postgres
(4 rows)
```

## 📖 Studi Kasus Docker Compose (Spring Boot Docker menggunakan Database dan Docker Volume)

Kita akan menggunakan contoh pada studi kasus sebelumnya menggunakan pendekatan docker compose.

![docker spring boot postgres with volume](/img/docker5.png)

### ✅ Database Postgresql

1. Kita akan membuat docker compose dengan catatan network dan volume sudah ada, dan membuat service p

```yaml
services:
  postgresql:
    container_name: postgresql
    image: postgres:17.4
    environment:
      - POSTGRES_PASSWORD=postgres
    networks:
      - topekox-network
    volumes:
      - topekox-volume:/var/lib/postgresql/data
networks:
  topekox-network:
    driver: bridge
    external: true
volumes:
  topekox-volume:
    driver: local
```

2. `docker compose up -d`.

Output: 

```
[+] Running 1/1
 ✔ Container postgresql              Started 
```

3. Masuk ke bash postgresql melalui bash container:

```
$ docker exec -it postgresql bash

root@4c772857215a:/# psql -U postgres
psql (17.4 (Debian 17.4-1.pgdg120+2))
Type "help" for help.

postgres=# create database springbootapp;
CREATE DATABASE
postgres=# 
```

### ✅ Membuat Backend

1. Menambahkan service spring boot pada docker compose:

```yaml
services:
  postgresql:
    container_name: postgresql
    image: postgres:17.4
    environment:
      - POSTGRES_PASSWORD=postgres
    networks:
      - topekox-network
    volumes:
      - topekox-volume:/var/lib/postgresql/data
  springboot:
    image: topekox/springboot-dto-pagination:0.0.1
    networks:
      - topekox-network
    environment:
      - DATABASE_ADDR=postgresql
      - DATABASE_USERNAME=postgres
      - DATABASE_PASSWORD=postgres
      - DATABASE_NAME=springbootapp
networks:
  topekox-network:
    driver: bridge
    external: true
volumes:
  topekox-volume:
    driver: local
```

2. Karena pada contoh kasus ini kita akan membuat 2 aplikasi spring boot dari image yang sama kita perlu melakukan scaling.

```
docker compose up --scale springboot=2 -d
```

Output:

```
[+] Running 3/3
 ✔ Container postgresql              Started                                                                    0.3s 
 ✔ Container example12-springboot-1  Started                                                                    0.7s 
 ✔ Container example12-springboot-2  Started    
 ```

> __Penting__: Dalam compose scale, tidak diperkenangkan menggunakan `container_name`, karena docker akan membuat name service secara otomatis secara berurutan.

### ✅ Membuat Service Nginx

1. Membuat `load-balancer.conf` dan `Dockerfile`

* `Dockerfile`

```docker
FROM nginx:stable-bullseye
COPY load-balancer.conf /etc/nginx/conf.d/default.conf
```

* `load-balancer.conf`, sesuaikan nama container dengan service yang dibuat docker compose:

```
upstream backend {
  server example12-springboot-1:8080;
  server example12-springboot-2:8080;
}

server {
  listen 80;

  location / {
    proxy_pass http://backend;
  }
}
```

2. Build menjadi image

```
docker image buiild -t topekox/nginx:0.0.2 .
```

3. Menambahkan service nginx pada docker compose:

```yaml
services:  
  postgresql:
    container_name: postgresql
    image: postgres:17.4
    environment:
      - POSTGRES_PASSWORD=postgres
    networks:
      - topekox-network
    volumes:
      - topekox-volume:/var/lib/postgresql/data
  springboot:
    image: topekox/springboot-dto-pagination:0.0.1
    networks:
      - topekox-network
    environment:
      - DATABASE_ADDR=postgresql
      - DATABASE_USERNAME=postgres
      - DATABASE_PASSWORD=postgres
      - DATABASE_NAME=springbootapp
  nginx:
    image: topekox/nginx:0.0.2
    ports:
      - 80:80
    networks:
      - topekox-network
networks:
  topekox-network:
    driver: bridge
    external: true
volumes:
  topekox-volume:
    driver: local
```

4. Docker Compose Up

```
$ docker compose up --scale springboot=2 -d

[+] Running 4/4
 ✔ Container example12-nginx-1       Started                                                                    0.5s 
 ✔ Container example12-springboot-1  Started                                                                    0.5s 
 ✔ Container postgresql              Started                                                                    0.4s 
 ✔ Container example12-springboot-2  Started                           
 ```

 5. Uji coba dengan menggunakan cURL, postman dll. ke alamat `localhost:80/api/product`.
