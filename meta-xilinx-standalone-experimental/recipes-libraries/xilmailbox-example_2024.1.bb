inherit esw deploy

ESW_COMPONENT_SRC = "/lib/sw_services/xilmailbox/examples/"

DEPENDS += "xilmailbox"

do_configure:prepend() {
    (
    cd ${S}
    lopper ${DTS_FILE} -- baremetallinker_xlnx.py ${ESW_MACHINE} ${S}/${ESW_COMPONENT_SRC}
    install -m 0755 *.cmake ${S}/${ESW_COMPONENT_SRC}/
    )
}

ESW_CUSTOM_LINKER_FILE ?= "None"
EXTRA_OECMAKE = "-DCUSTOM_LINKER_FILE=${@d.getVar('ESW_CUSTOM_LINKER_FILE')}"

do_generate_eglist () {
    cd ${S}
    lopper ${DTS_FILE} -- bmcmake_metadata_xlnx.py ${ESW_MACHINE} ${S}/${ESW_COMPONENT_SRC} hwcmake_metadata ${S}
    install -m 0755 *.cmake ${S}/${ESW_COMPONENT_SRC}/
}

addtask generate_eglist before do_configure after do_prepare_recipe_sysroot
do_prepare_recipe_sysroot[rdeptask] = "do_unpack"

do_install() {
    install -d ${D}/${base_libdir}/firmware
    install -m 0755  ${B}/*.elf ${D}/${base_libdir}/firmware
}

do_deploy() {
    install -d ${DEPLOYDIR}/${BPN}/
    install -Dm 0644 ${UNPACKDIR}/package/${base_libdir}/firmware/*.elf ${DEPLOYDIR}/${BPN}/
}
addtask deploy before do_build after do_package

FILES:${PN} = "${base_libdir}/firmware/*.elf"
