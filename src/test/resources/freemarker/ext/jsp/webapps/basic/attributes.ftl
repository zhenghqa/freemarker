<#assign t = JspTaglibs["http://freemarker.org/test/taglibs/test"]>

a1: ${a1!'-'}
<@t.getAndSet name="a1" value=0 />
a1: ${a1}
<@t.getAndSet name="a1" value=1 scope="page" />
a1: ${a1}
<@t.getAndSet name="a1" value=2 scope="request" />
<@t.getAndSet name="a1" value=3 scope="session" />
<@t.getAndSet name="a1" value=4 scope="application" />
Request.a1: ${Request.a1}
Session.a1: ${Session.a1}
Application.a1: ${Application.a1}
a1: ${a1}
<@t.getAndSet name="a1" value=null scope="page" />
a1: ${a1}
<@t.getAndSet name="a1" value=null scope="request" />
a1: ${a1}
<@t.getAndSet name="a1" value=null scope="session" />
a1: ${a1}
<@t.getAndSet name="a1" value=null scope="application" />
a1: ${a1!'-'}

a2: ${a2!'-'}
<@t.getAndSet name="a2" value=4 scope="application" />
a2: ${a2}
<@t.getAndSet name="a2" value=3 scope="session" />
a2: ${a2}
<@t.getAndSet name="a2" value=2 scope="request" />
a2: ${a2}
<@t.getAndSet name="a2" value=1 scope="page" />
a2: ${a2}

<#global a1 = 'G1'>
${a1}
<@t.getAndSet name="a1" value='P1' scope="page" />
${a1}

<#global a2 = 'G2'>
${a2}
<@t.getAndSet name="a2" value='P2' scope="page" />
${a2}
