#!/bin/bash

set -e

if [ $# -ne 1 ]; then
  echo "Usage: $0 <vespa version>"
  exit 1
fi

DIR=$(cd $(dirname "${BASH_SOURCE[0]}") && pwd)
cd $DIR

VERSION=$1
CALLER_UID=$(id -u)
CALLER_GID=$(id -g)

cd $DIR/..
mkdir -p /home/work/zhengzhejun/rpmbuild/{SOURCES,SPECS}
GZIP=-1 tar -zcf /home/work/zhengzhejun/rpmbuild/SOURCES/vespa-$VERSION.tar.gz --exclude target --exclude cmake-build-debug --transform "flags=r;s,^,vespa-$VERSION/," *
sed -e "s,VESPA_VERSION,$VERSION,"  < ./vespa.spec > /home/work/zhengzhejun/rpmbuild/SPECS/vespa-$VERSION.spec

yum-config-manager --add-repo https://copr.fedorainfracloud.org/coprs/g/vespa/vespa/repo/epel-7/group_vespa-vespa-epel-7.repo
yum -y install epel-release
yum -y install centos-release-scl yum-utils
yum -y install git bind-utils net-tools
yum -y --enablerepo=epel-testing install \
        ccache \
        cmake3 \
        devtoolset-7-binutils \
        devtoolset-7-gcc-c++ \
        devtoolset-7-libatomic-devel \
        flex \
        bison \
        git \
        java-1.8.0-openjdk-devel \
        Judy-devel \
        libicu-devel \
        libzstd-devel \
        llvm3.9-devel \
        llvm3.9-static \
        lz4-devel \
        make \
        rh-maven33 \
        openssl \
        openssl-devel \
        perl \
        perl-Data-Dumper \
        perl-Env \
        perl-IO-Socket-IP \
        perl-JSON \
        perl-libwww-perl \
        perl-Net-INET6Glue \
        perl-URI \
        rpm-build \
        sudo \
        valgrind \
        'vespa-boost-devel >= 1.59.0-7' \
        'vespa-cppunit-devel >= 1.12.1-7' \
        'vespa-libtorrent-devel >= 1.0.11-7' \
        'vespa-zookeeper-c-client-devel >= 3.4.9-7' \
        zlib-devel
yum clean all
echo "source /opt/rh/devtoolset-7/enable" >> /etc/profile.d/devtoolset-7.sh
echo "source /opt/rh/rh-maven33/enable" >> /etc/profile.d/devtoolset-7.sh
echo "*          soft    nproc     32768" > /etc/security/limits.d/90-nproc.conf
yum-builddep -y /home/work/zhengzhejun/rpmbuild/SPECS/vespa-${VERSION}.spec
rpmbuild -bb /home/work/zhengzhejun/rpmbuild/SPECS/vespa-${VERSION}.spec --define "_topdir /home/work/zhengzhejun/rpmbuild"
chown ${CALLER_UID}:${CALLER_GID} /home/work/zhengzhejun/rpmbuild/RPMS/x86_64/*.rpm
yum localinstall -y $(ls /home/work/zhengzhejun/rpmbuild/RPMS/x86_64/vespa*-${VERSION}-*.rpm | xargs)

VESPA_HOME=/home/work/vespa
PATH="${PATH}:${VESPA_HOME}/bin"

export VESPA_CONFIG_SERVERS=$(hostname)

${VESPA_HOME}/bin/vespa-start-configserver
# Give config server some time to come up before starting services
sleep 5
${VESPA_HOME}/bin/vespa-start-services

# Print log forever
while true; do
  ${VESPA_HOME}/bin/vespa-logfmt -f /opt/vespa/logs/vespa/vespa.log
  sleep 10
done
