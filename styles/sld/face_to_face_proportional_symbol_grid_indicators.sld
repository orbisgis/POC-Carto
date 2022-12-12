<?xml version="1.0" encoding="UTF-8"?>
<UserStyle xmlns="http://www.opengis.net/sld" xmlns:sld="http://www.opengis.net/sld"
           xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc">
    <Name>Default Styler</Name>
    <FeatureTypeStyle>
    <Rule>
        <LineSymbolizer>
            <Stroke>
                <CssParameter name="stroke">
                    <ogc:Literal>#000000</ogc:Literal>
                </CssParameter>
                <CssParameter name="stroke-width">
                    <ogc:Literal>1</ogc:Literal>
                </CssParameter>
            </Stroke>
        </LineSymbolizer>
    </Rule>
    <Rule>
        <PointSymbolizer>
            <Graphic>
                <Mark>
                    <WellKnownName>semicircle</WellKnownName>
                    <Fill>
                        <CssParameter name="fill">#09324D</CssParameter>
                    </Fill>

                </Mark>
                <Size>
                    <ogc:Function name="Interpolate">
                        <ogc:PropertyName>POP_0_5_YEARS</ogc:PropertyName>
                        <ogc:Literal>0</ogc:Literal>
                        <ogc:Literal>10</ogc:Literal>
                        <ogc:Literal>10</ogc:Literal>
                        <ogc:Literal>50</ogc:Literal>
                        <ogc:Literal>100</ogc:Literal>
                        <ogc:Literal>100</ogc:Literal>
                        <ogc:Literal>numeric</ogc:Literal>
                        <ogc:Literal>linear</ogc:Literal>
                    </ogc:Function>
                </Size>
            </Graphic>
        </PointSymbolizer>
    </Rule>
    <Rule>
        <PointSymbolizer>
            <Graphic>
                <Mark>
                    <WellKnownName>semicircle</WellKnownName>
                    <Fill>
                        <CssParameter name="fill">#f44336</CssParameter>
                    </Fill>
                </Mark>
                <Size>
                    <ogc:Function name="Interpolate">
                        <ogc:PropertyName>POP_GREATER_65_YEARS</ogc:PropertyName>
                        <ogc:Literal>0</ogc:Literal>
                        <ogc:Literal>10</ogc:Literal>
                        <ogc:Literal>10</ogc:Literal>
                        <ogc:Literal>50</ogc:Literal>
                        <ogc:Literal>100</ogc:Literal>
                        <ogc:Literal>100</ogc:Literal>
                        <ogc:Literal>numeric</ogc:Literal>
                        <ogc:Literal>linear</ogc:Literal>
                    </ogc:Function>
                </Size>
                <Rotation>180.0</Rotation>
            </Graphic>
        </PointSymbolizer>
    </Rule>
</FeatureTypeStyle>
        </UserStyle>