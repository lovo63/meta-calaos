DESCRIPTION = "SqueezeCenter"
LICENSE = "GPLv2"


PV = "${DISTRO_VERSION}"
PR = "r53"
LIC_FILES_CHKSUM = "file://License.txt;md5=1c5138338dfe20ec44c8ac5a37d8dd00"


SQUEEZECENTER_SVN_MODULE ?= "trunk"

SRC_URI = "http://downloads.slimdevices.com/LogitechMediaServer_v7.7.2/logitechmediaserver-7.7.2-noCPAN.tgz \
	file://squeezecenter \
	file://custom-convert.conf"

SRC_URI[md5sum] = "d4eb5c0b9085ca9dc3ebb8dd57857083"
SRC_URI[sha256sum] = "26142b0b23b974d80665e97a4b64070bd2d602e5c07117dfc6cf05d5f9301b79"
	
S = "${WORKDIR}/logitechmediaserver-7.7.2-33893-noCPAN"

# This should match the list in Slim::Utils::OS::SqueezeOS::skipPlugins
# Actual included plugins determined by INCLUDED_PLUGINS list below
EXCLUDED_PLUGINS = ""
INCLUDED_PLUGINS = ""

# Apps
INCLUDED_PLUGINS += "Amazon Classical Deezer"
INCLUDED_PLUGINS += "LMA Mediafly MP3tunes Pandora Slacker"
INCLUDED_PLUGINS += "Facebook Flickr LastFM Live365 RadioTime RhapsodyDirect"
INCLUDED_PLUGINS += "Sirius Sounds WiMP SpotifyLogi Orange MOG"
EXCLUDED_PLUGING += "RSSNews Podcast InfoBrowser UPnP"

# Core features
INCLUDED_PLUGINS += "CLI DateTime Favorites InternetRadio AudioScrobbler SongScanner"
INCLUDED_PLUGINS += "Base.pm OPMLBased.pm OPMLGeneric AppGallery MyApps"
INCLUDED_PLUGINS += "RandomPlay SavePlaylist SN"

# ip3k features
INCLUDED_PLUGINS += "DigitalInput LineIn LineOut"
EXCLUDED_PLUGINS += "RS232 Visualizer SlimTris Snow NetTest"

# SqueezePlay applets/extras
EXCLUDED_PLUGINS += "Extensions JiveExtras"

# Desktop stuff
EXCLUDED_PLUGINS += "iTunes MusicMagic PreventStandby Rescan TT xPL"

dirs755 = "${sysconfdir}/init.d \
	${sysconfdir}/squeezecenter ${sysconfdir}/squeezecenter/prefs ${sysconfdir}/squeezecenter/cache"

do_install() {
	mkdir -p ${D}/${prefix}/squeezecenter
	cp -a ${WORKDIR}/logitechmediaserver-7.7.2-33893-noCPAN/* ${D}/${prefix}/squeezecenter
	rm -r ${D}/${prefix}/squeezecenter/Bin
	
	# Deal with images
	mv ${D}/${prefix}/squeezecenter/HTML ${D}/${prefix}/squeezecenter/HTML.tmp
	mkdir -p ${D}/${prefix}/squeezecenter/HTML/Default/html/images
	mkdir -p ${D}/${prefix}/squeezecenter/HTML/EN/html/images
	for i in radio.png cover.png playlistclear.png playlistclear_40x40_m.png playlistsave.png playlistsave_40x40_m.png; do
		cp ${D}/${prefix}/squeezecenter/HTML.tmp/Default/html/images/$i ${D}/${prefix}/squeezecenter/HTML/Default/html/images
	done
	for i in artists genres years newmusic albums musicfolder search playall; do
		cp ${D}/${prefix}/squeezecenter/HTML.tmp/EN/html/images/$i.png ${D}/${prefix}/squeezecenter/HTML/EN/html/images
	done

	mv ${D}/${prefix}/squeezecenter/HTML.tmp/EN/html/errors ${D}/${prefix}/squeezecenter/HTML/Default/html
	rm -r ${D}/${prefix}/squeezecenter/HTML.tmp
	
	# Only include limited set of plugins
	mv ${D}/${prefix}/squeezecenter/Slim/Plugin ${D}/${prefix}/squeezecenter/Slim/Plugin.tmp
	mkdir ${D}/${prefix}/squeezecenter/Slim/Plugin
	for i in ${INCLUDED_PLUGINS}; do
		mv ${D}/${prefix}/squeezecenter/Slim/Plugin.tmp/$i ${D}/${prefix}/squeezecenter/Slim/Plugin
	done
	rm -r ${D}/${prefix}/squeezecenter/Slim/Plugin.tmp
	
	# Remove unneeded Slim modules
	rm -r ${D}/${prefix}/squeezecenter/Slim/Utils/ServiceManager*
	rm -r ${D}/${prefix}/squeezecenter/Slim/GUI
	
	# Leave firmware version files in place, just remove the binaries
	rm -r ${D}/${prefix}/squeezecenter/Firmware/*.bin
	
	rm -r ${D}/${prefix}/squeezecenter/Graphics/CODE2000.*

	# Remove duplicate modules under CPAN that were installed system-wide
	rm -r ${D}/${prefix}/squeezecenter/CPAN/arch
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Audio
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Class/XSAccessor*
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Compress
	rm -r ${D}/${prefix}/squeezecenter/CPAN/DBI.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/DBI
	rm -r ${D}/${prefix}/squeezecenter/CPAN/DBD
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Digest
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Encode
	rm -r ${D}/${prefix}/squeezecenter/CPAN/EV.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Font
	rm -r ${D}/${prefix}/squeezecenter/CPAN/HTML/Parser.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/HTML/Entities.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/HTML/Filter.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/HTML/HeadParser.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/HTML/LinkExtor.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/HTML/PullParser.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/HTML/TokeParser.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Image
	rm -r ${D}/${prefix}/squeezecenter/CPAN/JSON/XS.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/JSON/XS/Boolean.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser.pm       # Note: must keep custom Encodings
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/Japanese_Encodings.msg
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/README
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/euc-kr.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/big5.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/iso-8859-2.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/iso-8859-3.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/iso-8859-4.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/iso-8859-5.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/iso-8859-7.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/iso-8859-8.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/iso-8859-9.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/windows-1250.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/windows-1252.enc
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Encodings/x-*	
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Expat.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Parser/Style
	rm -r ${D}/${prefix}/squeezecenter/CPAN/YAML

	# Remove duplicate core Perl modules from CPAN tree
	rm -r ${D}/${prefix}/squeezecenter/CPAN/File/Spec*          # XXX: newer in 5.10
	rm -r ${D}/${prefix}/squeezecenter/CPAN/File/Temp.pm        # XXX: newer in 5.10
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Time/localtime.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Time/tm.pm
	
	# Save even more by removing CPAN modules SC on Fab4 won't need
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Archive             # plugins only
	rm -r ${D}/${prefix}/squeezecenter/CPAN/I18N                # web only
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Net/UPnP* 
	rm -r ${D}/${prefix}/squeezecenter/CPAN/PAR*                # plugins only
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Proc/Background/Win32.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Template*			# web only
	rm -r ${D}/${prefix}/squeezecenter/CPAN/Test
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/NamespaceSupport.pm
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/SAX*
	rm -r ${D}/${prefix}/squeezecenter/CPAN/XML/Writer.pm
	
	# Save even more by removing lib(CPAN) modules SC on Fab4 won't need
	rm -r ${D}/${prefix}/squeezecenter/lib/MPEG					# SB1 & SliMP3 only
	rm -r ${D}/${prefix}/squeezecenter/lib/Template				# web only

	# HTML files
	rm -r ${D}/${prefix}/squeezecenter/*.html
	find ${D}/${prefix}/squeezecenter/Slim/Plugin -name '*.html' -exec rm -r {} \;

	# ICU files (no ICU support for TinySC)
	rm -r ${D}/${prefix}/squeezecenter/icudt46*.dat

	echo "rev: 33893" > ${D}/${prefix}/squeezecenter/build.txt
	echo "repo: ${SRC_URI}" >> ${D}/${prefix}/squeezecenter/build.txt
	
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done
	install -m 0755 ${WORKDIR}/squeezecenter ${D}${sysconfdir}/init.d/squeezecenter	
	install -m 0755 ${WORKDIR}/custom-convert.conf ${D}/${prefix}/squeezecenter/custom-convert.conf	
}

FILES_${PN} += "${prefix}"

