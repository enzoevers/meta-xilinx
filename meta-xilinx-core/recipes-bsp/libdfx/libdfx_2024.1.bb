SUMMARY = "Xilinx libdfx library"
DESCRIPTION = "Xilinx libdfx Library and headers"

LICENSE = "MIT & GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=94aba86aec117f003b958a52f019f1a7"

BRANCH ?= "master"
REPO ?= "git://github.com/Xilinx/libdfx.git;protocol=https"
BRANCHARG = "${@['nobranch=1', 'branch=${BRANCH}'][d.getVar('BRANCH', True) != '']}"
SRC_URI = "${REPO};${BRANCHARG}"
SRCREV = "c8275891ead62b3dfce68c00cf466715f0ac75f1"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:zynqmp = "zynqmp"
COMPATIBLE_MACHINE:versal = "versal"

S = "${WORKDIR}/git"

inherit cmake

# Due to an update where the soname/version was defined, we need to use an RREPLACES
# so updates will work properly.
RREPLACES:${PN} = "libdfx"
