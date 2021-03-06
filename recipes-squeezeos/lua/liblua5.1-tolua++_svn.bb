DESCRIPTION = "LUA tolua++"
SECTION = "libs"
LICENSE = "unknown"

LIC_FILES_CHKSUM="file://README;md5=44270eb2516f3b4c1c8f5b035ad54705"

SRCREV = "9704"

BV = "1.0.92"

PV = "${BV}+svnr${SRCREV}"
#PR="r0"

DEPENDS = "lua5.1"

SRC_URI="${SQUEEZEPLAY_SCM};module=tolua++-${BV}"

S = "${WORKDIR}/tolua++-${BV}"

do_compile() {
	cd src/lib
	${CC} -fPIC -shared -I ${S}/include -o libtolua++.so *.c
}

do_install() {
	mkdir -p ${D}/${incdir}
	mkdir -p ${D}/${libdir}
	install -m 0755 include/tolua++.h ${D}/${includedir}
	install -m 0755 src/lib/libtolua++.so ${D}/${libdir}
        oe_libinstall -C src/lib libtolua++ ${STAGING_LIBDIR}/
        install -m 0755 include/tolua++.h ${STAGING_INCDIR}
}

FILES_${PN}-dbg = "${libdir}/.debug"
FILES_${PN}-dev = "${includedir}"
FILES_${PN} = "${libdir} ${datadir}"
