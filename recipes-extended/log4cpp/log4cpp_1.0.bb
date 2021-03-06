DESCRIPTION = "A library of C++ classes for flexible logging to files, syslog, IDSA and other destinations."
HOMEPAGE = "http://sourceforge.net/projects/log4cpp/"
PRIORITY = "optional"
PR = "r0"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"
SRC_URI[md5sum] = "1face50ead0790d1297dfb04bacf273c"
SRC_URI[sha256sum] = "b4533324b0a1f46ad74a9e92bf2caa46c246e9353059e1a835e735d41826ed19"
SRC_URI = "http://downloads.sourceforge.net/project/log4cpp/log4cpp-1.0.x%20%28current%29/${PN}-${PV}/${PN}-${PV}.tar.gz \
        file://0001_fix_build.patch"

inherit autotools pkgconfig

EXTRA_OECONF = "\
    --enable-doxygen=no \
    --enable-dot=no \
    --enable-html-docs=no \
    --enable-latex-docs=no \
"

