# Trials and tribulation of using the listShuttle #

We opted to use a listShuttle for this one screen in our app. Please do yourself a favor and don't use it. There are issues with conversion that I won't go into here.

There is also an issue with setting the width of the component. You can easily set the height with the listsHeight attribute, but there is no way to set the width. In short there is no listsWidth and to add insult to injury they hard-code the style so no matter how you tweak the CSS styles, you can't change the width.

The rich:listShuttle generates two list boxes (sort of), here is a snippet of HTML from said component:

```
                                            <td style="border:0px;padding : 0px;">
                                                <div class="rich-shuttle-list" id="rplForm:programSelectheaderBox">
                                                    <div class="rich-shuttle-list-content" id="rplForm:programSelectcontentBox"
                                                         style="width:140px;height:300px;">

```

Focus your attention on the style attribute above:

```
style="width:140px;height:300px;"
```

Setting listsHeight will change the height, i.e., listsHeight="400" equates to style="width:140px;height:400px;". There is no corresponding listsWidth and since the style is hard coded it can't be overridden with a style sheet and since they also ignore <form ... prependId="false", you can't set the class by id since CSS does not allow ":" for names.

Here is the workaround to this problem (uses prototype).


```
        <rich:listShuttle id="programSelect" sourceValue="#{rplController.availablePrograms}"
                          targetValue="#{rplController.selectedPrograms}" var="items"
                          listsHeight="300"
                          orderControlsVisible="false"
                          sourceCaptionLabel="Available Programs"
                          targetCaptionLabel="Selected Programs"
                          converter="${programConverter}">
            <rich:column>
                <h:outputText value="#{items}"></h:outputText>
            </rich:column>
        </rich:listShuttle>
        <script type="text/javascript">
            $("rplForm:programSelecttlContentBox").setStyle({width:'300px', height:'300px'})
            $("rplForm:programSelectcontentBox").setStyle({width:'300px', height:'300px'})
        </script>

```

I continue to like the idea of JSF, but the component libraries seem to be of dubious quality or perhaps I always pick the bad ones.