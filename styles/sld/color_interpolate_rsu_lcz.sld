<?xml version="1.0" encoding="UTF-8"?>
<UserStyle xmlns="http://www.opengis.net/sld" xmlns:sld="http://www.opengis.net/sld"
           xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc">
    <Name>Default Styler</Name>
    <FeatureTypeStyle>
        <Rule>
            <PolygonSymbolizer>
                <Fill>
                    <CssParameter name="fill">
                        <ogc:Function name="Interpolate">
                            <ogc:PropertyName>POP</ogc:PropertyName>
                            <ogc:Literal>0</ogc:Literal>
                            <ogc:Literal>#cc0000</ogc:Literal>
                            <ogc:Literal>345</ogc:Literal>
                            <ogc:Literal>#3d85c6</ogc:Literal>
                            <ogc:Literal>color</ogc:Literal>
                        </ogc:Function>
                    </CssParameter>
                </Fill>
                <Stroke>
                    <CssParameter name="stroke">
                        <ogc:Literal>#ffffff</ogc:Literal>
                    </CssParameter>
                    <CssParameter name="stroke-width">
                        <ogc:Literal>1</ogc:Literal>
                    </CssParameter>
                </Stroke>
            </PolygonSymbolizer>
        </Rule>
</FeatureTypeStyle>
        </UserStyle>